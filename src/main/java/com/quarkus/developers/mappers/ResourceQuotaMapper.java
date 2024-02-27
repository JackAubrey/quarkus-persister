package com.quarkus.developers.mappers;

import com.quarkus.developers.converters.DateConverter;
import com.quarkus.developers.entities.ResourceQuotaInfoEntity;
import com.quarkus.developers.services.messaging.events.ResourceQuotaInfoEvent;
import org.mapstruct.Mapper;

@Mapper(uses = DateConverter.class, componentModel = "jakarta")
public interface ResourceQuotaMapper {
    ResourceQuotaInfoEntity eventToEntity(ResourceQuotaInfoEvent event);

    ResourceQuotaInfoEvent eventToEntity(ResourceQuotaInfoEntity entity);
}
