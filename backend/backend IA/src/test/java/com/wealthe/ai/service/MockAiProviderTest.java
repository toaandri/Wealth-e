package com.wealthe.ai.service;

import com.wealthe.ai.config.AiProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("provideModelAndPromptCombinations")
    void testMockProviderVariousScenarios(String model, String prompt, int maxTokens, double temperature) {
        AiProperties properties = new AiProperties();
        properties.setEnabled(true);
        MockAiProvider provider = new MockAiProvider(properties);
        
        String response = provider.generateResponse(model, prompt, maxTokens, temperature);
        
        assertTrue(properties.isEnabled());
        assertTrue(response.contains("Mock AI response"));
        assertTrue(response.contains(model));
    }

    static Stream<Arguments> provideModelAndPromptCombinations() {
        return Stream.of(
            arguments("gpt-4", "Hello world", 512, 0.5),
            arguments("claude-3", "Complex prompt with unicode: éàù", 2048, 0.8),
            arguments("llama2", "", 4096, 1.0)
        );
    }
}