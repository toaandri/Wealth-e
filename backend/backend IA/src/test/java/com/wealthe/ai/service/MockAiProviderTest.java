package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MockAiProviderTest {

    @Test
    void testMockProviderDisabled() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(false);
        MockAiProvider provider = new MockAiProvider(properties);
        
        String response = provider.generateResponse("test-model", "Test prompt", 1000, 0.7);
        assertFalse(properties.isEnabled());
        assertTrue(response.contains("AI is disabled"));
    }

    @Test
    void testMockProviderEnabled() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(true);
        MockAiProvider provider = new MockAiProvider(properties);
        
        String response = provider.generateResponse("test-model", "Test prompt", 1000, 0.7);
        assertTrue(properties.isEnabled());
        assertTrue(response.contains("Mock AI response"));
        assertTrue(response.contains("test-model"));
        assertTrue(response.contains("1000"));
        assertTrue(response.contains("0.70"));
    }
}