package com.lpb.esb.service.common.model.request.settle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class RsGetBillDTO {

    @JsonProperty("transaction_id")
    private String transaction_id;
    @JsonProperty("last_event_seq_no")
    private String last_event_seq_no;
    @JsonProperty("last_proc_esr")
    private String last_proc_esr;
    @JsonProperty("trn_branch")
    private String trn_branch;
    @JsonProperty("customer_no")
    private String customer_no;
    @JsonProperty("request_chanel")
    private String request_chanel;
    @JsonProperty("service_provider")
    private String service_provider;
    @JsonProperty("trn_desc")
    private String trn_desc;
    @JsonProperty("request_ref_no")
    private String request_ref_no;
    @JsonProperty("trace_no")
    private String trace_no;
    @JsonProperty("service_row_id")
    private String service_row_id;
    @JsonProperty("service_id")
    private String service_id;
    @JsonProperty("product_code")
    private String product_code;
    @JsonProperty("request_account")
    private String request_account;
    @JsonProperty("receive_account")
    private String receive_account;
    @JsonProperty("total_amt")
    private String total_amt;
    @JsonProperty("connector_name")
    private String connector_name;
    @JsonProperty("url_api")
    private String url_api;
    @JsonProperty("connector_url")
    private String connector_url;
    @JsonProperty("method_action")
    private String method_action;
    @JsonProperty("service_type")
    private String service_type;
    @JsonProperty("executed_by")
    private String executed_by;
    @JsonProperty("bill_info")
    private List<RsBillDTO> listRsBillDTO;
}
