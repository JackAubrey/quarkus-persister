package com.quarkus.developers.services.persistance;

import com.quarkus.common.data.events.ClusterResourceEvent;

public interface PersistServiceFacade {
    void persist(ClusterResourceEvent payload);
}
