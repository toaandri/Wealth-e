package com.wealthe.ai.dto;

import java.util.UUID;
import java.util.List;

public class KnowledgeSearchRequest {

    private String query;
    private UUID portfolioId;
    private int limit;
    private boolean includeMetadata;

    public KnowledgeSearchRequest() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isIncludeMetadata() {
        return includeMetadata;
    }

    public void setIncludeMetadata(boolean includeMetadata) {
        this.includeMetadata = includeMetadata;
    }
}

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

public class KnowledgeDocument {

    public enum SourceType {
        LICENSED,
        CURATED,
        SYNTHETIC
    }

    public static class Metadata {
        private String title;
        private String version;
        private Instant lastUpdated;
        private String provenance;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Instant getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(Instant lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public String getProvenance() {
            return provenance;
        }

        public void setProvenance(String provenance) {
            this.provenance = provenance;
        }
    }
}