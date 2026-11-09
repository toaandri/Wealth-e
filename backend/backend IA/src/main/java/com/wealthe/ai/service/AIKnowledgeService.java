package com.wealthe.ai.service;

import com.wealthe.ai.dto.KnowledgeSearchRequest;
import com.wealthe.ai.dto.KnowledgeSearchResult;
import com.wealthe.ai.domain.KnowledgeChunk;
import com.wealthe.ai.domain.KnowledgeDocument;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AIKnowledgeService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<KnowledgeSearchResult> search(KnowledgeSearchRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<KnowledgeChunk> query = cb.createQuery(KnowledgeChunk.class);
        Root<KnowledgeChunk> root = query.from(KnowledgeChunk.class);

        if (request.getPortfolioId() != null) {
            // Join with document and filter by portfolio if needed
            // This is a simplified version - real implementation would join KnowledgeDocument
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            // Simple text-based search on content
            // In production, this would use pgvector or similar
            query.where(cb.like(cb.lower(root.get("content")), "%" + request.getQuery().toLowerCase() + "%"));
        }

        query.select(root);
        query.orderBy(cb.desc(root.get("createdAt")));

        if (request.getLimit() > 0) {
            query.setMaxResults(request.getLimit());
        } else {
            query.setMaxResults(10);
        }

        List<KnowledgeChunk> results = entityManager.createQuery(query).getResultList();

        List<KnowledgeSearchResult> response = new ArrayList<>();
        for (KnowledgeChunk chunk : results) {
            KnowledgeSearchResult result = new KnowledgeSearchResult();
            result.setId(chunk.getId());
            result.setContentPreview(chunk.getContent().length() > 200
                ? chunk.getContent().substring(0, 200) + "..."
                : chunk.getContent());
            result.setLastUpdated(chunk.getCreatedAt());

            // Get document metadata
            KnowledgeDocument doc = chunk.getDocument();
            if (doc != null) {
                result.setTitle(doc.getTitle());
                result.setSourceType(doc.getSource() != null ? KnowledgeDocument.SourceType.valueOf(doc.getSource().toUpperCase()) : KnowledgeDocument.SourceType.CURATED);
            }

            response.add(result);
        }

        return response;
    }

    public KnowledgeDocument saveDocument(KnowledgeDocument document) {
        document.setCreatedAt(Instant.now());
        return document; // EntityManager would persist in real app
    }
}