package com.quarkus.developers.mappers;

import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.developers.converters.DateConverter;
import com.quarkus.developers.entities.ResourceQuotaInfoEntity;
import org.mapstruct.Mapper;

@Mapper(uses = DateConverter.class, componentModel = "jakarta")
public interface ResourceQuotaMapper {
    ResourceQuotaInfoEntity eventToEntity(ResourceQuotaDto event);

    ResourceQuotaDto eventToEntity(ResourceQuotaInfoEntity entity);
}
