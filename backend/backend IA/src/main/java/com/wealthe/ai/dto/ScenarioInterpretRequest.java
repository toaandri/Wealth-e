package com.wealthe.ai.dto;

import java.util.UUID;

public class ScenarioInterpretRequest {

    private UUID portfolioId;
    private String naturalLanguageQuery;
    private boolean includeAssumptions;

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getNaturalLanguageQuery() {
        return naturalLanguageQuery;
    }

    public void setNaturalLanguageQuery(String naturalLanguageQuery) {
        this.naturalLanguageQuery = naturalLanguageQuery;
    }

    public boolean isIncludeAssumptions() {
        return includeAssumptions;
    }

    public void setIncludeAssumptions(boolean includeAssumptions) {
        this.includeAssumptions = includeAssumptions;
    }
}
