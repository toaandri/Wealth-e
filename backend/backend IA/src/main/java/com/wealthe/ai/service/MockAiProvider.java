package com.wealthe.ai.service;

import org.springframework.stereotype.Service;
import com.wealthe.ai.config.AiProperties;
import java.util.Locale;

@Service
public class MockAiProvider implements AiProvider {

    private final AiProperties aiProperties;

    public MockAiProvider(AiProperties aiProperties) {
        this.aiProperties = aiProperties;
    }

    @Override
    public String generateResponse(String model, String prompt, int maxTokens, double temperature) {
        if (!aiProperties.isEnabled()) {
            return "AI is disabled. Set AI_ENABLED=true to enable.";
        }
        return String.format(
            Locale.ROOT,
            "Mock AI response for prompt: %s... (model: %s, maxTokens: %d, temperature: %.2f)",
            prompt.substring(0, Math.min(50, prompt.length())),
            model,
            maxTokens,
            temperature
        );
    }
}