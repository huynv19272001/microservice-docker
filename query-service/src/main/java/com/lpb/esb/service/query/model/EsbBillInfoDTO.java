package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBillInfoDTO {
    private String senderCode;
    private String senderName;
    private String receiverCode;
    private String receiverName;
    private String msgId;
    private String msgRefid;
    private String sendDate;
    private String originalCode;
    private String originalName;
    private String originalDate;
    private String trnRefId;
    private String sendBank;
    private String receiveBank;
    private String createdDt;
    private String makerId;
    private String checkerId;
    private String requestRefNo;
    private String tranCode;
    private String trnDt;
    private String ccy;
    private String settleAmount;
    private String senderAccNo;
    private String senderAccDesc;
    private String senderBankCode;
    private String senderBankName;
    private String DRCRInd;
    private String receiveBankCode;
    private String receiveBankName;
    private String receiveAccNo;
    private String receiveAccDesc;
    private String trnDesc;
    private String spare1;
    private String spare2;
    private String spare3;
    private String version;
    private String errorCode;
    private String errorDesc;
    private String idLink;

    private String billCode;
    private String billDesc;
    private String amtUnit;
    private String settledAmount;
    private String otherInfo;
    private String billType;
    private String billStatus;
    private String billId;
    private String billAmount;
    private String paymentMethod;
}
