package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderCode")
    private String senderCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderName")
    private String senderName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiverCode")
    private String receiverCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiverName")
    private String receiverName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("msgRefid")
    private String msgRefid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sendDate")
    private String sendDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("originalCode")
    private String originalCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("originalName")
    private String originalName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("trnRefId")
    private String trnRefId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sendBank")
    private String sendBank;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiveBank")
    private String receiveBank;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("createdDt")
    private String createdDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("makerid")
    private String makerid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("checkerid")
    private String checkerid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("requestRefNo")
    private String requestRefNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("trnDt")
    private String trnDt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ccy")
    private String ccy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleAmount")
    private String settleAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderAccNo")
    private String senderAccNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderAccDesc")
    private String senderAccDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderBankCode")
    private String senderBankCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("senderBankName")
    private String senderBankName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("drcrInd")
    private String drcrInd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiveBankCode")
    private String receiveBankCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiveBankName")
    private String receiveBankName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiveAccNo")
    private String receiveAccNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("receiveAccDesc")
    private String receiveAccDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("trnDesc")
    private String trnDesc;
}
