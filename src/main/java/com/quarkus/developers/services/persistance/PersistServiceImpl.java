package com.quarkus.developers.services.persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.common.data.events.ClusterResourceEvent;
import com.quarkus.developers.entities.TestResourceDocument;
import com.quarkus.developers.repositories.TestResourceDocumentRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
public class PersistServiceImpl implements PersistService {
    private final TestResourceDocumentRepo repo;
    private final ObjectMapper mapper;

    public PersistServiceImpl(TestResourceDocumentRepo repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

//    @Transactional
    @Override
    public void persist(ClusterResourceEvent resource) {
        log.info("PERSIST || Received resource {}", resource);
        Object value = mapper.convertValue(resource.getPayloadContent(), getDtoClass(resource.getPayloadType()));
        TestResourceDocument test = new TestResourceDocument();
        test.setProducer("Foo");
        test.setPayloadContent(Map.of("K1", "V1", "K2", "V2"));
        test.setProducer("me");
        repo.persist(test);
    }

    private Class<?> getDtoClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
