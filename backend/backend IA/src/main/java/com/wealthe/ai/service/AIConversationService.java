package com.wealthe.ai.service;

import com.wealthe.ai.dto.ChatRequest;
import com.wealthe.ai.dto.ChatResponse;
import com.wealthe.ai.domain.ChatConversation;
import com.wealthe.ai.domain.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class AIConversationService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AIService aiService;

    public AIConversationService(AIService aiService) {
        this.aiService = aiService;
    }

    public ChatResponse processAndRecord(ChatRequest request, ChatConversation conversation) {
        ChatResponse response = aiService.processChat(request);
        if (conversation == null) {
            return response;
        }
        response.setConversationId(conversation.getId());

        ChatMessage userMessage = aiService.saveUserMessage(conversation, request.getMessage());
        entityManager.persist(userMessage);

        ChatMessage assistantMessage = aiService.saveAssistantMessage(
            conversation,
            response.getContent(),
            response.getToolName(),
            response.getToolCallsJson()
        );
        assistantMessage.setId(response.getMessageId());
        entityManager.persist(assistantMessage);

        return response;
    }

    public ChatConversation createConversation(UUID userId, String title) {
        ChatConversation conversation = new ChatConversation();
        conversation.setId(UUID.randomUUID());
        conversation.setUserId(userId);
        conversation.setTitle(title);
        conversation.setCreatedAt(Instant.now());
        conversation.setUpdatedAt(Instant.now());
        conversation.setActive(true);
        return conversation;
    }

    public void updateConversation(ChatConversation conversation) {
        conversation.setUpdatedAt(Instant.now());
    }
}