package com.quarkus.developers.services.persistance;

import com.quarkus.developers.entities.ClusterResourceEntity;
import com.quarkus.developers.repositories.ClusterResourceEntityRepo;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ClusterResourceEntityPersist implements EntityPersist {
    private final ClusterResourceEntityRepo repo;

    ClusterResourceEntityPersist(ClusterResourceEntityRepo repo) {
        this.repo = repo;
    }

    @Override
    public void persist(ClusterResourceEntity entity) {
        repo.persist(entity);
    }
}
