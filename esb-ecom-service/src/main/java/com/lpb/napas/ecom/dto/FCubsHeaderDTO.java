package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FCubsHeaderDTO {
    private String source;
    private String password;
    private String ubsComp;
    private String appId;
    private String msgId;
    private String userId;
    private String branch;
    private String moduleId;
    private String service;
    private String operation;
    private String functionId;
    private String action;
    private String msgStat;

    private String sourceOperation;
    private String sourceUserId;
    private String destination;
    private String multitripId;
    private String referenceNo;
    private String correlId;
}
