package com.wealthe.ai.dto;

import java.time.Instant;

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
