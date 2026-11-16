package com.wealthe.ai.controller;

import com.wealthe.ai.config.AiProperties;
import com.wealthe.ai.dto.*;
import com.wealthe.ai.domain.ChatConversation;
import com.wealthe.ai.domain.ChatMessage;
import com.wealthe.ai.service.AIConversationService;
import com.wealthe.ai.service.AIKnowledgeService;
import com.wealthe.ai.service.AIScenarioInterpretationService;
import com.wealthe.ai.service.AIService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class AIRestController {

    @Autowired
    private AIService aiService;

    @Autowired
    private AIConversationService conversationService;

    @Autowired
    private AIScenarioInterpretationService scenarioInterpretationService;

    @Autowired
    private AIKnowledgeService knowledgeService;

    @Autowired
    private AiProperties aiProperties;

    @PersistenceContext
    private EntityManager entityManager;

    // GET /api/v1/ai/conversations - List conversations for user
    @GetMapping("/conversations")
    public ResponseEntity<java.util.List<ConversationResponse>> listConversations(
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<ConversationResponse> result = entityManager.createQuery(
                        "select c from ChatConversation c where c.userId = :userId order by c.updatedAt desc",
                        ChatConversation.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(this::toConversationResponse)
                .toList();
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/ai/conversations - Create new conversation
    @PostMapping("/conversations")
    @Transactional
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestBody ConversationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        ChatConversation conversation = conversationService.createConversation(
                UUID.fromString(userDetails.getUsername()), request.getTitle());
        entityManager.persist(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(toConversationResponse(conversation));
    }

    // GET /api/v1/ai/conversations/{id}/messages - Get messages for conversation
    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<java.util.List<MessageResponse>> getMessages(
            @PathVariable("conversationId") UUID conversationId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ChatConversation conversation = entityManager.find(ChatConversation.class, conversationId);
        if (conversation == null || !conversation.getUserId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }

        List<MessageResponse> result = entityManager.createQuery(
                        "select m from ChatMessage m where m.conversation.id = :conversationId order by m.createdAt",
                        ChatMessage.class)
                .setParameter("conversationId", conversationId)
                .getResultList()
                .stream()
                .map(this::toMessageResponse)
                .toList();
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/ai/chat - Send a chat message
    @PostMapping("/chat")
    @Transactional
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (!aiProperties.isEnabled()) {
            ChatResponse disabledResponse = new ChatResponse();
            disabledResponse.setAiEnabled(false);
            disabledResponse.setContent("AI is disabled. Enable AI features to use this endpoint.");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(disabledResponse);
        }

        UUID userId = UUID.fromString(userDetails.getUsername());
        ChatConversation conversation;
        if (request.getConversationId() == null) {
            conversation = conversationService.createConversation(userId, null);
            entityManager.persist(conversation);
        } else {
            conversation = entityManager.find(ChatConversation.class, request.getConversationId());
            if (conversation == null || !conversation.getUserId().equals(userId)) {
                return ResponseEntity.notFound().build();
            }
        }

        ChatResponse response = conversationService.processAndRecord(request, conversation);
        conversationService.updateConversation(conversation);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/ai/scenarios/interpret - Interpret natural language scenario
    @GetMapping("/scenarios/interpret")
    public ResponseEntity<ScenarioInterpretResponse> interpretScenario(
            @RequestParam("portfolioId") UUID portfolioId,
            @RequestParam("query") String query) {
        ScenarioInterpretRequest request = new ScenarioInterpretRequest();
        request.setPortfolioId(portfolioId);
        request.setNaturalLanguageQuery(query);

        ScenarioInterpretResponse response = scenarioInterpretationService.interpret(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/ai/reports/explain - Explain portfolio changes
    @GetMapping("/reports/explain")
    public ResponseEntity<ReportExplainResponse> explainReport(
            @RequestParam("portfolioId") UUID portfolioId,
            @RequestParam(required = false, defaultValue = "net_worth_change") String focusArea,
            @RequestParam(required = false, defaultValue = "monthly") String timePeriod) {
        ReportExplainRequest request = new ReportExplainRequest();
        request.setPortfolioId(portfolioId);
        request.setFocusArea(focusArea);
        request.setTimePeriod(timePeriod);

        // In real implementation, would call report service
        ReportExplainResponse response = new ReportExplainResponse();
        response.setExplanationId(UUID.randomUUID());
        response.setPortfolioId(portfolioId);
        response.setNetWorthChange(java.math.BigDecimal.ZERO);
        response.setGrossAssetsChange(java.math.BigDecimal.ZERO);
        response.setLiabilitiesChange(java.math.BigDecimal.ZERO);
        response.setAttributionBreakdown(java.util.Collections.emptyList());
        response.setDisclaimers(java.util.List.of(
                "Generated from backend deterministic calculations",
                "Model output is untrusted - verify with backend data"
        ));
        response.setGeneratedAt(Instant.now());

        return ResponseEntity.ok(response);
    }

    // GET /api/v1/ai/knowledge/search - Search knowledge base
    @GetMapping("/knowledge/search")
    public ResponseEntity<java.util.List<KnowledgeSearchResult>> searchKnowledge(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false) UUID portfolioId,
            @RequestParam(defaultValue = "10") int limit) {
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery(query);
        request.setPortfolioId(portfolioId);
        request.setLimit(limit);

        java.util.List<KnowledgeSearchResult> results = knowledgeService.search(request);
        return ResponseEntity.ok(results);
    }

    // Helper classes for request/response DTOs

    public static class ConversationRequest {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private ConversationResponse toConversationResponse(ChatConversation conversation) {
        ConversationResponse response = new ConversationResponse();
        response.setId(conversation.getId());
        response.setTitle(conversation.getTitle());
        response.setUserId(conversation.getUserId());
        response.setCreatedAt(conversation.getCreatedAt());
        response.setUpdatedAt(conversation.getUpdatedAt());
        response.setActive(conversation.isActive());
        return response;
    }

    private MessageResponse toMessageResponse(ChatMessage message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setRole(message.getRole());
        response.setContent(message.getContent());
        response.setCreatedAt(message.getCreatedAt());
        response.setToolName(message.getToolName());
        response.setToolCallsJson(message.getToolCallsJson());
        return response;
    }
}