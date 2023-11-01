package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostInfoDTO {
    private String sourceAcc;
    private String acNo;
    private String branchCode;
    private String customerNo;
    private String ccy;
    private String fcyAmount;
    private String lcyAmount;
    private String drcrInd;
    private String amountTag;
    private String bankCode;
    private String bankName;
    private String makerId;
    private String checkerId;
    private String infoSourceAcc;
    private String infoAcNo;
    private String postRefNo;
    private String postDesc;
    private String cardNo;
    private String serviceType;
}
