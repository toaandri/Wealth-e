package com.wealthe.ai.dto;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatRequestTest {

    @Test
    void testDefaultConstructor() {
        ChatRequest request = new ChatRequest();
        assertNull(request.getConversationId());
        assertNull(request.getMessage());
        assertFalse(request.isIncludeTools());
        assertNull(request.getModelOverride());
    }

    @Test
    void testAllArgsConstructor() {
        UUID conversationId = UUID.randomUUID();
        ChatRequest request = new ChatRequest();
        request.setConversationId(conversationId);
        request.setMessage("Test message");
        request.setIncludeTools(true);
        request.setModelOverride("custom-model");
        
        assertEquals(conversationId, request.getConversationId());
        assertEquals("Test message", request.getMessage());
        assertTrue(request.isIncludeTools());
        assertEquals("custom-model", request.getModelOverride());
    }

    @Test
    void testSetters() {
        ChatRequest request = new ChatRequest();
        request.setConversationId(UUID.randomUUID());
        assertNotNull(request.getConversationId());
        
        request.setMessage("New message");
        assertEquals("New message", request.getMessage());
        
        request.setIncludeTools(false);
        assertFalse(request.isIncludeTools());
        
        request.setModelOverride("new-model");
        assertEquals("new-model", request.getModelOverride());
    }
}