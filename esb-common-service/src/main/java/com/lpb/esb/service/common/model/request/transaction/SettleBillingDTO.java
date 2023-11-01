package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettleBillingDTO {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("last_event_seq_no")
    private String lastEventSeqNo;
    @JsonProperty("last_proc_esr")
    private String lastProcEsr;
    @JsonProperty("trn_branch")
    private String trnBranch;
    @JsonProperty("")
    private String ebkUser;
    @JsonProperty("customer_no")
    private String customerNo;
    @JsonProperty("request_chanel")
    private String requestChanel;
    @JsonProperty("request_by")
    private String requestBy;
    @JsonProperty("service_provider")
    private String serviceProvider;
    @JsonProperty("confirm_trn")
    private String confirmTrn;
    @JsonProperty("trn_desc")
    private String trnDesc;
    @JsonProperty("")
    private String traceNo;
    @JsonProperty("service_row_id")
    private String serviceRowId;
    @JsonProperty("service_info")
    private ServiceDTO serviceInfo;
    @JsonProperty("partner_info")
    private PartnerDTO partnerInfo;
    @JsonProperty("bill_info")
    private List<BillDTO> billInfo;
    @JsonProperty("total_amt")
    private String totalAmt;
    @JsonProperty("connector_name")
    private String connectorName;
    @JsonProperty("url_api")
    private String urlApi;
    @JsonProperty("connector_url")
    private String connectorUrl;
    @JsonProperty("")
    private String connectorPort;
    @JsonProperty("method_action")
    private String methodAction;
    @JsonProperty("service_type")
    private String serviceType;
    @JsonProperty("executed_by")
    private String executedBy;
}
