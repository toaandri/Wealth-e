package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIServiceTest {

    @Test
    void testProcessChatWithMockProviderEnabled() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(true);
        
        AIService service = new AIService(new MockAiProvider(properties), properties);
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Hello, how are you?");
        
        ChatResponse response = service.processChat(request);
        
        assertNotNull(response.getMessageId());
        assertEquals("assistant", response.getRole());
        assertNotNull(response.getContent());
        assertTrue(response.isAiEnabled());
    }

    @Test
    void testProcessChatWithDisabledProvider() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(false);
        
        AIService service = new AIService(new MockAiProvider(properties), properties);
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Hello");
        
        ChatResponse response = service.processChat(request);
        
        assertFalse(response.isAiEnabled());
        assertTrue(response.getContent().contains("AI is disabled"));
    }

    @Test
    void testProcessChatWithCustomModel() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(true);
        properties.setModelName("custom-model");
        
        AIService service = new AIService(new MockAiProvider(properties), properties);
        
        ChatRequest request = new ChatRequest();
        request.setMessage("Test message");
        request.setModelOverride("override-model");
        
        ChatResponse response = service.processChat(request);
        
        assertTrue(response.getContent().contains("override-model"));
    }
}