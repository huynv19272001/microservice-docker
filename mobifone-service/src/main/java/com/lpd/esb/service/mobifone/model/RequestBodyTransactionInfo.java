package com.lpd.esb.service.mobifone.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestBodyTransactionInfo {
    private String tranDt;
    private String tranCode;
    private String tranDesc;
    private String merchantId;
    private String branch;
    private String customerNo;
    private String settleAmount;
    private String channel;
    private String tranRefNo;
}
