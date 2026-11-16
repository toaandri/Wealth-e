package com.wealthe.ai.dto;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScenarioInterpretRequestTest {

    @Test
    void testDefaultConstructor() {
        ScenarioInterpretRequest request = new ScenarioInterpretRequest();
        assertNull(request.getPortfolioId());
        assertNull(request.getNaturalLanguageQuery());
        assertFalse(request.isIncludeAssumptions());
    }

    @Test
    void testAllArgsConstructor() {
        UUID portfolioId = UUID.randomUUID();
        ScenarioInterpretRequest request = new ScenarioInterpretRequest();
        request.setPortfolioId(portfolioId);
        request.setNaturalLanguageQuery("Test query");
        request.setIncludeAssumptions(true);
        
        assertEquals(portfolioId, request.getPortfolioId());
        assertEquals("Test query", request.getNaturalLanguageQuery());
        assertTrue(request.isIncludeAssumptions());
    }

    @Test
    void testSetters() {
        ScenarioInterpretRequest request = new ScenarioInterpretRequest();
        request.setPortfolioId(UUID.randomUUID());
        assertNotNull(request.getPortfolioId());
        
        request.setNaturalLanguageQuery("New query");
        assertEquals("New query", request.getNaturalLanguageQuery());
        
        request.setIncludeAssumptions(false);
        assertFalse(request.isIncludeAssumptions());
    }
}