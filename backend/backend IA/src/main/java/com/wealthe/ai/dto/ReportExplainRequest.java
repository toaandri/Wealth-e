package com.wealthe.ai.dto;

import java.util.UUID;

public class ReportExplainRequest {

    private UUID portfolioId;
    private String focusArea; // e.g., "net_worth_change", "performance", "fee_analysis"
    private String timePeriod; // e.g., "monthly", "quarterly", "year_to_date"

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getFocusArea() {
        return focusArea;
    }

    public void setFocusArea(String focusArea) {
        this.focusArea = focusArea;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}

public class AttributeInfo {

    private String type;
    private String label;
    private java.math.BigDecimal value;
    private String currency;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    java.math.BigDecimal getValue() {
        return value;
    }

    void setValue(java.math.BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

public class ReportExplainResponse {

    private UUID explanationId;
    private UUID portfolioId;
    private java.math.BigDecimal netWorthChange;
    private java.math.BigDecimal grossAssetsChange;
    private java.math.BigDecimal liabilitiesChange;
    private java.util.List<AttributeInfo> attributionBreakdown;
    private java.util.List<String> disclaimers;
    private Instant generatedAt;

    public UUID getExplanationId() {
        return explanationId;
    }

    public void setExplanationId(UUID explanationId) {
        this.explanationId = explanationId;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    java.math.BigDecimal getNetWorthChange() {
        return netWorthChange;
    }

    void setNetWorthChange(java.math.BigDecimal netWorthChange) {
        this.netWorthChange = netWorthChange;
    }

    java.math.BigDecimal getGrossAssetsChange() {
        return grossAssetsChange;
    }

    void setGrossAssetsChange(java.math.BigDecimal grossAssetsChange) {
        this.grossAssetsChange = grossAssetsChange;
    }

    java.math.BigDecimal getLiabilitiesChange() {
        return liabilitiesChange;
    }

    void setLiabilitiesChange(java.math.BigDecimal liabilitiesChange) {
        this.liabilitiesChange = liabilitiesChange;
    }

    java.util.List<AttributeInfo> getAttributionBreakdown() {
        return attributionBreakdown;
    }

    void setAttributionBreakdown(java.util.List<AttributeInfo> attributionBreakdown) {
        this.attributionBreakdown = attributionBreakdown;
    }

    java.util.List<String> getDisclaimers() {
        return disclaimers;
    }

    void setDisclaimers(java.util.List<String> disclaimers) {
        this.disclaimers = disclaimers;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }
}