package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIConversationServiceTest {

    @Test
    void testCreateConversation() {
        AIConversationService service = new AIConversationService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), "Test Conversation");
        
        assertNotNull(conversation.getId());
        assertEquals("Test Conversation", conversation.getTitle());
        assertNotNull(conversation.getCreatedAt());
        assertNotNull(conversation.getUpdatedAt());
        assertTrue(conversation.isActive());
    }

    @Test
    void testProcessAndRecord() {
        AIConversationService service = new AIConversationService();
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Test message");
        
        ChatResponse response = service.processAndRecord(request, null);
        
        assertNotNull(response.getMessageId());
        assertEquals("assistant", response.getRole());
        assertNotNull(response.getContent());
    }
}