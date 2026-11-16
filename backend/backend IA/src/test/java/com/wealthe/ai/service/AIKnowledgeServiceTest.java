package com.wealthe.ai.service;

import com.wealthe.ai.domain.KnowledgeDocument;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AIKnowledgeServiceTest {

    @Test
    void saveDocumentPersistsAndReturnsTheDocument() {
        EntityManager entityManager = mock(EntityManager.class);
        AIKnowledgeService service = new AIKnowledgeService(entityManager);
        KnowledgeDocument document = new KnowledgeDocument();

        KnowledgeDocument saved = service.saveDocument(document);

        assertSame(document, saved);
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        verify(entityManager).persist(document);
    }
}
