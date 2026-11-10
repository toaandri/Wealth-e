package com.wealthe.ai.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIKnowledgeServiceTest {

    @Test
    void testSearchWithEmptyQuery() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("");
        
        // This should not throw exception
        var results = service.search(request);
        
        // Should return empty or default list
        assertNotNull(results);
    }

    @Test
    void testSearchWithQuery() {
        AIKnowledgeService service = new AIKnowledgeService();
        
        KnowledgeSearchRequest request = new KnowledgeSearchRequest();
        request.setQuery("test query");
        
        var results = service.search(request);
        
        assertNotNull(results);
    }
}