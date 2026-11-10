package com.wealthe.ai.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AiPropertiesTest {

    @Test
    void testDefaultValues() {
        AiProperties properties = new AiProperties();
        assertFalse(properties.isEnabled());
        assertEquals("mock", properties.getProvider());
        assertEquals("http://localhost:11434", properties.getOllamaBaseUrl());
        assertEquals("llama2", properties.getModelName());
        assertEquals(4096, properties.getMaxTokens());
        assertEquals(0.7, properties.getTemperature());
        assertEquals(8192, properties.getContextWindow());
        assertFalse(properties.isStreaming());
    }

    @Test
    void testSetters() {
        AiProperties properties = new AiProperties();
        properties.setEnabled(true);
        assertTrue(properties.isEnabled());
        
        properties.setProvider("ollama");
        assertEquals("ollama", properties.getProvider());
        
        properties.setModelName("mistral");
        assertEquals("mistral", properties.getModelName());
        
        properties.setMaxTokens(2048);
        assertEquals(2048, properties.getMaxTokens());
        
        properties.setTemperature(0.5);
        assertEquals(0.5, properties.getTemperature());
        
        properties.setContextWindow(4096);
        assertEquals(4096, properties.getContextWindow());
        
        properties.setStreaming(true);
        assertTrue(properties.isStreaming());
    }
}