package com.quarkus.developers.services.persistance;

import com.quarkus.developers.entities.ClusterResourceDocument;

interface DocumentPersist {
    void persist(ClusterResourceDocument document);
}
