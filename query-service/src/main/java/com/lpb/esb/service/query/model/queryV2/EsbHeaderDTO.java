package com.lpb.esb.service.query.model.queryV2;

import lombok.*;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbHeaderDTO {
    private String source;
    private String ubsComp;
    private String referenceNo;
    private String msgId;
    private String correlId;
    private String userId;
    private String branch;
    private String moduleId;
    private String serviceId;
    private String service;
    private String operation;
    private String productCode;
    private String sourceOperation;
    private String sourceUserId;
    private String destination;
    private String multiTripId;
    private String functionId;
    private String action;
    private String msgStat;
    private String password;
}
