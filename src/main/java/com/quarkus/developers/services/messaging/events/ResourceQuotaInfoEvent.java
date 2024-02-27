package com.quarkus.developers.services.messaging.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ResourceQuotaInfoEvent {
    private String name;
    private String namespace;
    private Map<String, BigDecimal> specHard;
    private Map<String, BigDecimal> statusHard;
    private Map<String, BigDecimal> statusUsed;

}
