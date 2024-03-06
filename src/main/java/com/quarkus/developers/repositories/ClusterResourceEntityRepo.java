package com.quarkus.developers.repositories;

import com.quarkus.developers.entities.ClusterResourceEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClusterResourceEntityRepo implements PanacheRepository<ClusterResourceEntity> {
}
