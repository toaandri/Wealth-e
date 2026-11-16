package com.wealthe.ai.dto;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConversationResponseTest {

    @Test
    void testDefaultConstructor() {
        ConversationResponse response = new ConversationResponse();
        assertNull(response.getId());
        assertNull(response.getTitle());
        assertNull(response.getUserId());
        assertNull(response.getCreatedAt());
        assertNull(response.getUpdatedAt());
        assertFalse(response.isActive());
        assertNull(response.getMessages());
    }

    @Test
    void testAllArgsConstructor() {
        UUID convId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        java.time.Instant now = java.time.Instant.now();
        java.util.List<MessageResponse> messages = java.util.Collections.emptyList();
        
        ConversationResponse response = new ConversationResponse();
        response.setId(convId);
        response.setTitle("Test Title");
        response.setUserId(userId);
        response.setCreatedAt(now);
        response.setUpdatedAt(now);
        response.setActive(true);
        response.setMessages(messages);
        
        assertEquals(convId, response.getId());
        assertEquals("Test Title", response.getTitle());
        assertEquals(userId, response.getUserId());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
        assertTrue(response.isActive());
        assertEquals(messages, response.getMessages());
    }

    @Test
    void testSetters() {
        ConversationResponse response = new ConversationResponse();
        UUID id = UUID.randomUUID();
        response.setId(id);
        assertEquals(id, response.getId());
        
        response.setTitle("New Title");
        assertEquals("New Title", response.getTitle());
        
        UUID userId = UUID.randomUUID();
        response.setUserId(userId);
        assertEquals(userId, response.getUserId());
        
        java.time.Instant now = java.time.Instant.now();
        response.setCreatedAt(now);
        assertEquals(now, response.getCreatedAt());
        
        response.setUpdatedAt(now);
        assertEquals(now, response.getUpdatedAt());
        
        response.setActive(false);
        assertFalse(response.isActive());
    }
}