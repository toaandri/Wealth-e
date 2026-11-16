package com.wealthe.ai.service;

import com.wealthe.ai.config.AiProperties;
import com.wealthe.ai.domain.ChatConversation;
import com.wealthe.ai.dto.ChatRequest;
import com.wealthe.ai.dto.ChatResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AIConversationServiceTest {

    private AIConversationService createService() {
        AiProperties properties = new AiProperties();
        AIService aiService = new AIService((model, prompt, maxTokens, temperature) -> "response", properties);
        return new AIConversationService(aiService);
    }

    @Test
    void testCreateConversation() {
        AIConversationService service = createService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), "Test Conversation");
        
        assertNotNull(conversation.getId());
        assertEquals("Test Conversation", conversation.getTitle());
        assertNotNull(conversation.getCreatedAt());
        assertNotNull(conversation.getUpdatedAt());
        assertTrue(conversation.isActive());
    }

    @Test
    void testCreateConversationWithNullTitle() {
        AIConversationService service = createService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), null);
        
        assertNotNull(conversation.getId());
        assertNull(conversation.getTitle());
        assertNotNull(conversation.getCreatedAt());
        assertTrue(conversation.isActive());
    }

    @Test
    void testUpdateConversation() {
        AIConversationService service = createService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), "Original");
        service.updateConversation(conversation);
        
        assertNotNull(conversation.getUpdatedAt());
    }

    @Test
    void testProcessAndRecordWithMessage() {
        AIConversationService service = createService();
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Test message for processing");
        
        ChatResponse response = service.processAndRecord(request, null);
        
        assertNotNull(response.getMessageId());
        assertEquals("assistant", response.getRole());
        assertNotNull(response.getContent());
    }
}