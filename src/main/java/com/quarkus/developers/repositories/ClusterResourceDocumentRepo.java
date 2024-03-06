package com.quarkus.developers.repositories;

import com.quarkus.developers.entities.ClusterResourceDocument;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClusterResourceDocumentRepo implements PanacheMongoRepository<ClusterResourceDocument> {
}
