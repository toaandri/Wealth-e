package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIKnowledgeServiceTest {

    @Test
    void testSearchWithEmptyQuery() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("");
        
        var results = service.search(request);
        
        assertNotNull(results);
    }

    @Test
    void testSearchWithNonEmptyQuery() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("test query");
        
        var results = service.search(request);
        
        assertNotNull(results);
    }

    @Test
    void testSearchWithLimit() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("test");
        request.setLimit(5);
        
        var results = service.search(request);
        
        assertNotNull(results);
    }

    @Test
    void testSearchWithPortfolioId() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("test");
        request.setPortfolioId(UUID.randomUUID());
        
        var results = service.search(request);
        
        assertNotNull(results);
    }
}