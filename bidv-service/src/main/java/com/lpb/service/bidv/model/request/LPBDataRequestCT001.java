package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LPBDataRequestCT001 {
    //f1
//    @Column(length = 12)
    @JsonProperty("SenderBankId")
    private String senderBankId;
    //f2
//    @Column(length = 12)
    @JsonProperty("ReceiveBankId")
    private String receiveBankId;
    //f3
//    @Column(length = 16)
    @JsonProperty("ReqRefNo")
    private String reqRefNo;
    //f4
//    @Column(length = 4)
    @JsonProperty("OperationCode")
    private String operationCode;
    //f5
//    @Column(length = 6)
    @JsonProperty("ValueDate")
    private String valueDate;
    //f6
//    @Column(length = 3)
    @JsonProperty("TxnCcy")
    private String txnCcy;
    //f7
//    @Column(length = 15)
    @JsonProperty("TxnAmount")
    private String txnAmount;
    //f8
//    @Column(length = 34)
    @JsonProperty("SendersAccount")
    private String sendersAccount;
    //f9
//    @Column(length = 140)
    @JsonProperty("SendersName")
    private String sendersName;
    //f10
//    @Column(length = 11)
    @JsonProperty("OrderingInstitution")
    private String orderingInstitution;
    //f11
//    @Column(length = 11)
    @JsonProperty("BensBankCode")
    private String bensBankCode;
    //f12
//    @Column(length = 34)
    @JsonProperty("BensAccount")
    private String bensAccount;
    //f13
//    @Column(length = 140)
    @JsonProperty("BensName")
    private String bensName;
    //f14
//    @Column(length = 140)
    @JsonProperty("TrnDesc")
    private String trnDesc;
    //f15
//    @Column(length = 3)
    @JsonProperty("Charge")
    private String charge;
    //f16
//    @Column(length = 70)
    @JsonProperty("SenderToReceiverInformation")
    private String senderToReceiverInformation;
    //f17
//    @Column(length = 15)
    @JsonProperty("CurrencyInstructedAmount")
    private String currencyInstructedAmount;
//    @Column(length = 3)
    @JsonProperty("MsgType")
    private String msgType;
//    @Column(length = 10)
    @JsonProperty("Status")
    private String status;
}

