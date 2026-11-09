package com.wealthe.ai.controller;

import com.wealthe.ai.config.AiProperties;
import com.wealthe.ai.dto.*;
import com.wealthe.ai.domain.ChatConversation;
import com.wealthe.ai.service.AIConversationService;
import com.wealthe.ai.service.AIKnowledgeService;
import com.wealthe.ai.service.AIScenarioInterpretationService;
import com.wealthe.ai.service.AIService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

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
        // In a real implementation, query conversations by user
        // This is a simplified mock response
        java.util.List<ConversationResponse> result = java.util.Collections.emptyList();
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
        ConversationResponse response = new ConversationResponse();
        response.setId(conversation.getId());
        response.setTitle(conversation.getTitle());
        response.setUserId(conversation.getUserId());
        response.setCreatedAt(conversation.getCreatedAt());
        response.setUpdatedAt(conversation.getUpdatedAt());
        response.setActive(conversation.isActive());
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/ai/conversations/{id}/messages - Get messages for conversation
    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<java.util.List<MessageResponse>> getMessages(
            @PathVariable("conversationId") UUID conversationId) {
        // In real implementation, fetch messages for this conversation
        java.util.List<MessageResponse> result = java.util.Collections.emptyList();
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/ai/chat - Send a chat message
    @PostMapping("/chat")
    @Transactional
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (!aiProperties.isEnabled()) {
            return ResponseEntity
                    .status(404)
                    .body(ChatResponse.builder()
                            .aiEnabled(false)
                            .content("AI is disabled. Enable AI features to use this endpoint.")
                            .build());
        }

        ChatResponse response = conversationService.processAndRecord(request, null);
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
}