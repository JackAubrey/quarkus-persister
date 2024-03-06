package com.quarkus.developers.services.persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.quarkus.common.data.dtos.DeploymentDto;
import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.common.data.dtos.ServiceDto;
import com.quarkus.common.data.events.ClusterResourceEvent;
import com.quarkus.developers.entities.ClusterResourceDocument;
import com.quarkus.developers.entities.ClusterResourceEntity;
import com.quarkus.developers.entities.PayloadType;
import com.quarkus.developers.exceptions.PersistServiceFacadeException;
import com.quarkus.developers.mappers.ClusterResourceMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class PersistServiceFacadeImpl implements PersistServiceFacade {

    private final DocumentPersist documentPersist;
    private final EntityPersist entityPersist;
    private final ClusterResourceMapper resourceMapper;
    private final ObjectMapper objectMapper;


    public PersistServiceFacadeImpl(DocumentPersist documentPersist,
                                    EntityPersist entityPersist,
                                    ClusterResourceMapper resourceMapper,
                                    ObjectMapper objectMapper) {
        this.documentPersist = documentPersist;
        this.entityPersist = entityPersist;
        this.resourceMapper = resourceMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void persist(ClusterResourceEvent event) {
        log.info("PERSIST || Received event {}", event);
        documentPersist.persist(toDocument(event));
        entityPersist.persist(toEntity(event));
    }

    private ClusterResourceEntity toEntity(ClusterResourceEvent event) {
        Map<String, Object> eventContent = event.getPayloadContent();
        ClusterResourceEntity entity = resourceMapper.eventToEntity(event);
        entity.setPayloadType(resolvePayloadType(event));
        //entity.setCreatedDate(OffsetDateTime.now());

        try {
            Map<String, String> value2 = new JavaPropsMapper().writeValueAsMap(eventContent);
            Map<String, Object> values = JsonFlattener.flattenAsMap(objectMapper.writeValueAsString(eventContent));

            System.out.println("############################################################"
                    + "\n## BY Jackson Props Mapper"
                    + "\n############################################################"
                    + objectMapper.writeValueAsString(value2)
                    + "\n############################################################");

            System.out.println("############################################################"
                    + "\n## BY Json Flattener"
                    + "\n############################################################"
                    + objectMapper.writeValueAsString(values)
                    + "\n############################################################");
            return entity;
        } catch(Exception e ) {
            throw new PersistServiceFacadeException("Unable to persist Cluster Resource Entity:", e);
        }
    }

    private ClusterResourceDocument toDocument(ClusterResourceEvent event) {
        ClusterResourceDocument document = resourceMapper.eventToDocument(event);
        document.setPayloadType(resolvePayloadType(event));
        //document.setCreatedDate(OffsetDateTime.now());
        return document;
    }

    private PayloadType resolvePayloadType(ClusterResourceEvent event) {
        String payloadClass = event.getPayloadClass();

        if(DeploymentDto.class.getName().equals(payloadClass)) {
            return PayloadType.DEPLOY;
        } else if(PodDto.class.getName().equals(payloadClass)) {
            return PayloadType.POD;
        } else if(ResourceQuotaDto.class.getName().equals(payloadClass)) {
            return PayloadType.RESOURCE_QUOTA;
        } else if(ServiceDto.class.getName().equals(payloadClass)) {
            return PayloadType.SERVICE;
        } else {
            throw new IllegalArgumentException("Unsupported payload: "+payloadClass);
        }
    }
}
