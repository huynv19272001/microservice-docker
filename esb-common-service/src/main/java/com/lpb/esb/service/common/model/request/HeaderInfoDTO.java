package com.lpb.esb.service.common.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class HeaderInfoDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ubsComp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String referenceNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msgId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String correlId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branch;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String moduleId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String serviceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String service;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sourceOperation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sourceUserId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String destination;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String multiTripId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String functionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String action;
}
