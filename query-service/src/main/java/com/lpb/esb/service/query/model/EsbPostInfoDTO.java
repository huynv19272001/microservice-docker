package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbPostInfoDTO {
    private String sourceAcc;
    private String accNo;
    private String branchCode;
    private String ccy;
    private String fcyAmount;
    private String lcyAmount;
    private String DRCRInd;
    private String amountTag;
    private String bankCode;
    private String bankName;
    private String makerId;
    private String checkerId;
    private String infoSourceAcc;
    private String infoAcNo;
    private String postDesc;
    private String postRefNo;
}
