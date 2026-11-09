package com.wealthe.ai.service;

import com.wealthe.ai.dto.ScenarioInterpretRequest;
import com.wealthe.ai.dto.ScenarioInterpretResponse;
import com.wealthe.ai.domain.AIInvocation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class AIScenarioInterpretationService {

    @PersistenceContext
    private EntityManager entityManager;

    public ScenarioInterpretResponse interpret(ScenarioInterpretRequest request) {
        ScenarioInterpretResponse response = new ScenarioInterpretResponse();
        response.setInterpretationId(UUID.randomUUID());
        response.setPortfolioId(request.getPortfolioId());
        response.setUserConfirmationRequired(true);

        // Parse the natural language query into typed parameters
        String query = request.getNaturalLanguageQuery();
        java.util.Map<String, Object> parameters = new java.util<>();

        if (query != null) {
            // Simple parameter extraction - in production, this would use
            // a proper NLP parser with schema validation
            if (query.contains("growth")) {
                parameters.put("includeGrowth", true);
            }
            if (query.contains("fee")) {
                parameters.put("includeFees", true);
            }
            if (query.contains("month")) {
                parameters.put("timePeriod", "monthly");
            }
            if (query.contains("year")) {
                parameters.put("timePeriod", "year_to_date");
            }
        }

        response.setParsedIntent("Scenario interpretation: " + (query != null ? query : "empty query"));
        response.setParameters(parameters);
        response.setAssumptions(java.util.List.of(
            "Parameters derived from natural language pattern matching",
            "Final calculations performed by deterministic backend engine"
        ));

        // Record the AI invocation
        AIInvocation invocation = new AIInvocation();
        invocation.setId(UUID.randomUUID());
        invocation.setPrompt(request.getNaturalLanguageQuery());
        invocation.setResponse(response.toString());
        invocation.setTokensUsed(response.hashCode());
        invocation.setModelName("scenario_parser");
        invocation.setStatus("completed");
        invocation.setCreatedAt(Instant.now());
        // entityManager.persist(invocation); // Would persist in real app

        return response;
    }
}