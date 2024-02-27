package com.quarkus.developers.repositories;

import com.quarkus.developers.entities.ResourceQuotaInfoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceQuotaInfoRepo implements PanacheRepository<ResourceQuotaInfoEntity> {
}
