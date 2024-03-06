package com.quarkus.developers.services.persistance;

import com.quarkus.common.data.events.ClusterResourceEvent;

public interface PersistService {
    void persist(ClusterResourceEvent payload);
}
