package com.wealthe.ai.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScenarioInterpretResponse {

    private UUID interpretationId;
    private UUID portfolioId;
    private String parsedIntent;
    private Map<String, Object> parameters;
    private List<String> assumptions;
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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public List<String> getAssumptions() {
        return assumptions;
    }

    public void setAssumptions(List<String> assumptions) {
        this.assumptions = assumptions;
    }

    public boolean isUserConfirmationRequired() {
        return userConfirmationRequired;
    }

    public void setUserConfirmationRequired(boolean userConfirmationRequired) {
        this.userConfirmationRequired = userConfirmationRequired;
    }
}
