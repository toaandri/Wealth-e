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
    void testCreateConversationWithNullTitle() {
        AIConversationService service = new AIConversationService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), null);
        
        assertNotNull(conversation.getId());
        assertNull(conversation.getTitle());
        assertNotNull(conversation.getCreatedAt());
        assertTrue(conversation.isActive());
    }

    @Test
    void testUpdateConversation() {
        AIConversationService service = new AIConversationService();
        
        ChatConversation conversation = service.createConversation(UUID.randomUUID(), "Original");
        service.updateConversation(conversation);
        
        assertNotNull(conversation.getUpdatedAt());
    }

    @Test
    void testProcessAndRecordWithMessage() {
        AIConversationService service = new AIConversationService();
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Test message for processing");
        
        ChatResponse response = service.processAndRecord(request, null);
        
        assertNotNull(response.getMessageId());
        assertEquals("assistant", response.getRole());
        assertNotNull(response.getContent());
    }
}