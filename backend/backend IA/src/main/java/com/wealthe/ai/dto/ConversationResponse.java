package com.wealthe.ai.dto;

import java.util.UUID;

public class ConversationResponse {

    private UUID id;
    private String title;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean isActive;
    private java.util.List<MessageResponse> messages;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    java.util.List<MessageResponse> getMessages() {
        return messages;
    }

    void setMessages(java.util.List<MessageResponse> messages) {
        this.messages = messages;
    }
}