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
public class RequestTransactionDTO {
    @JsonProperty("trn_brn")
    private String trnBrn;
    @JsonProperty("customer_no")
    private String customerNo;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("trn_desc")
    private String trnDesc;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("confirm_trn")
    private String confirmTrn;
    @JsonProperty("service_info")
    private ServiceDTO serviceInfo;
    @JsonProperty("partner_info")
    private PartnerDTO partnerInfo;
    @JsonProperty("customer_info")
    private CustomerDTO customerInfo;
    @JsonProperty("post_info")
    private List<PostInfoDTO> postInfo;
}
