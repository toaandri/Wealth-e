package com.wealthe.ai.dto;

import java.time.Instant;
import java.util.UUID;

public class KnowledgeSearchResult {

    private UUID id;
    private String title;
    private KnowledgeDocument.SourceType sourceType;
    private double relevanceScore;
    private String contentPreview;
    private Instant lastUpdated;
    private KnowledgeDocument.Metadata metadata;

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

    public KnowledgeDocument.SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(KnowledgeDocument.SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    public String getContentPreview() {
        return contentPreview;
    }

    public void setContentPreview(String contentPreview) {
        this.contentPreview = contentPreview;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public KnowledgeDocument.Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(KnowledgeDocument.Metadata metadata) {
        this.metadata = metadata;
    }
}
