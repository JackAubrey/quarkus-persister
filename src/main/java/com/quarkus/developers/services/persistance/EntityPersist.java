package com.quarkus.developers.services.persistance;

import com.quarkus.developers.entities.ClusterResourceEntity;

interface EntityPersist {
    void persist(ClusterResourceEntity entity);
}
