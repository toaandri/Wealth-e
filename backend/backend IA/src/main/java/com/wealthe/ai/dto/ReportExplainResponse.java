package com.wealthe.ai.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReportExplainResponse {

    private UUID explanationId;
    private UUID portfolioId;
    private BigDecimal netWorthChange;
    private BigDecimal grossAssetsChange;
    private BigDecimal liabilitiesChange;
    private List<AttributeInfo> attributionBreakdown;
    private List<String> disclaimers;
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

    public BigDecimal getNetWorthChange() {
        return netWorthChange;
    }

    public void setNetWorthChange(BigDecimal netWorthChange) {
        this.netWorthChange = netWorthChange;
    }

    public BigDecimal getGrossAssetsChange() {
        return grossAssetsChange;
    }

    public void setGrossAssetsChange(BigDecimal grossAssetsChange) {
        this.grossAssetsChange = grossAssetsChange;
    }

    public BigDecimal getLiabilitiesChange() {
        return liabilitiesChange;
    }

    public void setLiabilitiesChange(BigDecimal liabilitiesChange) {
        this.liabilitiesChange = liabilitiesChange;
    }

    public List<AttributeInfo> getAttributionBreakdown() {
        return attributionBreakdown;
    }

    public void setAttributionBreakdown(List<AttributeInfo> attributionBreakdown) {
        this.attributionBreakdown = attributionBreakdown;
    }

    public List<String> getDisclaimers() {
        return disclaimers;
    }

    public void setDisclaimers(List<String> disclaimers) {
        this.disclaimers = disclaimers;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }
}
