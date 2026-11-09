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
public class AIService {

    private final AiProvider aiProvider;
    private final AiProperties aiProperties;

    public AIService(AiProvider aiProvider, AiProperties aiProperties) {
        this.aiProvider = aiProvider;
        this.aiProperties = aiProperties;
    }

    public ChatResponse processChat(ChatRequest request) {
        String responseText = aiProvider.generateResponse(
            request.getModelOverride() != null ? request.getModelOverride() : aiProperties.getModelName(),
            request.getMessage(),
            aiProperties.getMaxTokens(),
            aiProperties.getTemperature()
        );

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setMessageId(UUID.randomUUID());
        chatResponse.setRole("assistant");
        chatResponse.setContent(responseText);
        chatResponse.setCreatedAt(Instant.now());
        chatResponse.setAiEnabled(aiProperties.isEnabled());

        return chatResponse;
    }

    @Transactional
    public ChatMessage saveUserMessage(ChatConversation conversation, String content) {
        ChatMessage message = new ChatMessage();
        message.setConversation(conversation);
        message.setRole("user");
        message.setContent(content);
        message.setCreatedAt(Instant.now());
        return message; // EntityManager would persist in real app
    }

    @Transactional
    public ChatMessage saveAssistantMessage(ChatConversation conversation, String content, String toolName, String toolCallsJson) {
        ChatMessage message = new ChatMessage();
        message.setConversation(conversation);
        message.setRole("assistant");
        message.setContent(content);
        message.setToolName(toolName);
        message.setToolCallsJson(toolCallsJson);
        message.setCreatedAt(Instant.now());
        return message;
    }
}