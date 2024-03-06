package com.quarkus.common.data.dtos;

import lombok.Data;

@Data
public class ServiceStatusConditionDto {
    private String status;
    private String type;
    private String lastTransitionTime;
    private String message;
    private String reason;
}
