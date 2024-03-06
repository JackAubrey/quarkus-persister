package com.quarkus.developers.repositories;

import com.quarkus.developers.entities.TestResourceDocument;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestResourceDocumentRepo implements PanacheMongoRepository<TestResourceDocument> {
}
