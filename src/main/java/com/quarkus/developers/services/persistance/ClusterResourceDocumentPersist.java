package com.quarkus.developers.services.persistance;

import com.quarkus.developers.entities.ClusterResourceDocument;
import com.quarkus.developers.repositories.ClusterResourceDocumentRepo;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ClusterResourceDocumentPersist implements DocumentPersist {
    private final ClusterResourceDocumentRepo repo;

    ClusterResourceDocumentPersist(ClusterResourceDocumentRepo repo) {
        this.repo = repo;
    }

    @Override
    public void persist(ClusterResourceDocument document) {
        repo.persist(document);
    }
}
