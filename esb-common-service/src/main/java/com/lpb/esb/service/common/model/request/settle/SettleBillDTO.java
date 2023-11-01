package com.lpb.esb.service.common.model.request.settle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.esb.service.common.model.request.infocustomerbill.BillDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.CustomerDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.ServiceDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.SettleAccountDTO;
import com.lpb.esb.service.common.model.request.transaction.BillingLogDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class SettleBillDTO {
    @JsonProperty("confirmTrn")
    private String confirmTrn;
    @JsonProperty("customerNo")
    private String customerNo;
    @JsonProperty("trnBrn")
    private String trnBrn;
    @JsonProperty("trnDesc")
    private String trnDesc;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("serviceLog")
    private BillingLogDTO serviceLog;
    @JsonProperty("partner")
    private PartnerDTO partner;
    @JsonProperty("service")
    private ServiceDTO service;
    @JsonProperty("listBillInfo")
    private List<BillDTO> listBillInfo;
    @JsonProperty("postInfo")
    private List<PostInfoDTO> listPostInfo;
    @JsonProperty("customerInfo")
    private CustomerDTO customerInfo;
    @JsonProperty("settleAccountInfo")
    private SettleAccountDTO settleAccountInfo;
}
