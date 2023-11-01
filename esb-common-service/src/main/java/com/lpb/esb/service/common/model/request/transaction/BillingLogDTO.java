package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillingLogDTO {
    @JsonProperty("service_info")
    private String billingLog;
    @JsonProperty("txn_req_esb")
    private String txnReqEsb;
    @JsonProperty("txn_res_esb")
    private String txnResEsb;
    @JsonProperty("txn_req_par")
    private String txnReqPar;
    @JsonProperty("txn_res_par")
    private String txnResPar;
}
