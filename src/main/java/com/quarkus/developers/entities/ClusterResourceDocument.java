package com.quarkus.developers.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@MongoEntity(collection="ClusterResources")
public class ClusterResourceDocument {
    @Id
    public ObjectId id;

    @Version
    private Long version;

    @CreationTimestamp
    private OffsetDateTime createdDate;
    private String producer;
    private OffsetDateTime created;
    private PayloadType payloadType;
    private String payloadClass;

    private Map<String, Object> attributes;
}
