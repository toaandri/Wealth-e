package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIScenarioInterpretationServiceTest {

    @Test
    void testInterpretScenario() {
        AIScenarioInterpretationService service = new AIScenarioInterpretationService();
        
        ScenarioInterpretRequest request = new ScenarioInterpretRequest();
        request.setPortfolioId(UUID.randomUUID());
        request.setQuery("Test natural language query about growth and fees");
        
        var response = service.interpret(request);
        
        assertNotNull(response.getInterpretationId());
        assertNotNull(response.getParsedIntent());
        assertNotNull(response.getParameters());
        assertTrue(response.isUserConfirmationRequired());
    }
}