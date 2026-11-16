package com.wealthe.ai.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    private UUID conversationId;
    @NotBlank
    private String message;
    private boolean includeTools;
    private String modelOverride;

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIncludeTools() {
        return includeTools;
    }

    public void setIncludeTools(boolean includeTools) {
        this.includeTools = includeTools;
    }

    public String getModelOverride() {
        return modelOverride;
    }

    public void setModelOverride(String modelOverride) {
        this.modelOverride = modelOverride;
    }
}
