package com.quarkus.developers.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@MongoEntity(collection="MyCollection")
public class TestResourceDocument {
    public ObjectId id;
    private String producer;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime created;
    private String payloadType;
    private Map<String, Object> payloadContent;
}
