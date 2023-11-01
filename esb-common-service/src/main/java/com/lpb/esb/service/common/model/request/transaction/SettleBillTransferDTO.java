package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettleBillTransferDTO {
    private String transactionId;
    private String lastEventSeqNo;
    private String lastProcEsr;
    private String trnBranch;
    private String ebkUser;
    private String customerNo;
    private String requestBy;
    private String responseBy;
    private String trnDesc;
    private String serviceRowId;
    private ServiceDTO serviceInfo;
    private PartnerDTO partnerInfo;
    private List<AccountInfoDTO> txnAccount;
    private String txnAmount;
    private String txnCcy;
    private String drcrInd;
    private String traceNo;
    private String connectorName;
    private String urlApi;
    private String connectorUrl;
    private String connectorPort;
    private String methodPort;
    private String serviceType;
    private String executedBy;
}
