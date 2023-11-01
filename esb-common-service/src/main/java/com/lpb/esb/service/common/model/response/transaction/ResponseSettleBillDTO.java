package com.lpb.esb.service.common.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSettleBillDTO {
    private String transactionId;
    private String trnBranch;
    private String customerNo;
    private String requestBy;
    private String requestChanel;
    private String serviceProvider;
    private String trnDesc;
    private String serviceId;
    private String product;
    private String serviceType;
    // transfer
    private String txnAccount;
    private String txnAmount;
    private String txnCcy;
    // Billing
    private String requestAccount;
    private String receiveAccount;
    private String billInfo;
    private String totalAmount;

    private String reqRefNo;
    private String traceNo;
    private String connectorName;
    private String executedBy;
    private String urlApi;
    private String connectorUrl;
    private String connectorPort;
    private String methodAction;
    private String providerId;
    private String privateKey;
    private String trnDt;
    private String valueDt;
    private String other;
}
