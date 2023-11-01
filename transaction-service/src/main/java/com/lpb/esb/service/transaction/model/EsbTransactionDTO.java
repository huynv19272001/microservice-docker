package com.lpb.esb.service.transaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbTransactionDTO {
    private String senderCode;
    private String senderName;
    private String receiverCode;
    private String receiverName;
    private String msgId;
    private String msgRefid;
    private String originalCode;
    private String originalName;
    private String originalDate;

    private String sendBank;
    private String branch;
    private String merchantId;
    private String customerNo;
    private String receiveBank;
    private String createdDt;
    private String makerId;
    private String checkerId;

    private String sendDate;

    private String tranRefNo;
    private String tranDesc;
    private String tranCode;
    private String tranDt;

    private String spare1;
    private String spare2;
    private String spare3;
    private String version;
    private String errorCode;
    private String errorDesc;
    private String idLink;

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

    private String channel;
    private String terminalId;
}
