package com.wealthe.ai.service;

public interface AiProvider {
    String generateResponse(String model, String prompt, int maxTokens, double temperature);

    default String generateStreamingResponse(String model, String prompt, int maxTokens, double temperature) {
        return generateResponse(model, prompt, maxTokens, temperature);
    }
}