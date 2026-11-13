package com.wealthe.ai.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    @Test
    void testDefaultConstructor() {
        MessageResponse response = new MessageResponse();
        assertNull(response.getId());
        assertNull(response.getRole());
        assertNull(response.getContent());
        assertNull(response.getCreatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        UUID msgId = UUID.randomUUID();
        MessageResponse response = new MessageResponse(msgId, "user", "Hello!", null, null, null);
        
        assertEquals(msgId, response.getId());
        assertEquals("user", response.getRole());
        assertEquals("Hello!", response.getContent());
    }

    @Test
    void testSetters() {
        MessageResponse response = new MessageResponse();
        UUID id = UUID.randomUUID();
        response.setId(id);
        assertEquals(id, response.getId());
        
        response.setRole("assistant");
        assertEquals("assistant", response.getRole());
        
        response.setContent("New content");
        assertEquals("New content", response.getContent());
        
        Instant instant = Instant.now();
        response.setCreatedAt(instant);
        assertEquals(instant, response.getCreatedAt());
    }
}