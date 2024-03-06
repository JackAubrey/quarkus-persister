package com.quarkus.developers.mappers;


import com.quarkus.common.data.events.ClusterResourceEvent;
import com.quarkus.developers.converters.DateConverter;
import com.quarkus.developers.entities.ClusterResourceDocument;
import com.quarkus.developers.entities.ClusterResourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateConverter.class, componentModel = "jakarta")
public interface ClusterResourceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "payloadType", ignore = true)
    @Mapping(target = "version", ignore = true)
    ClusterResourceEntity eventToEntity(ClusterResourceEvent event);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "attributes", source = "payloadContent")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "payloadType", ignore = true)
    @Mapping(target = "version", ignore = true)
    ClusterResourceDocument eventToDocument(ClusterResourceEvent event);
}
