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

public class ScenarioInterpretResponse {

    private UUID interpretationId;
    private UUID portfolioId;
    private String parsedIntent;
    private java.util.Map<String, Object> parameters;
    private java.util.List<String> assumptions;
    private boolean userConfirmationRequired;

    public UUID getInterpretationId() {
        return interpretationId;
    }

    public void setInterpretationId(UUID interpretationId) {
        this.interpretationId = interpretationId;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getParsedIntent() {
        return parsedIntent;
    }

    public void setParsedIntent(String parsedIntent) {
        this.parsedIntent = parsedIntent;
    }

    java.util.Map<String, Object> getParameters() {
        return parameters;
    }

    void setParameters(java.util.Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    java.util.List<String> getAssumptions() {
        return assumptions;
    }

    void setAssumptions(java.util.List<String> assumptions) {
        this.assumptions = assumptions;
    }

    public boolean isUserConfirmationRequired() {
        return userConfirmationRequired;
    }

    public void setUserConfirmationRequired(boolean userConfirmationRequired) {
        this.userConfirmationRequired = userConfirmationRequired;
    }
}