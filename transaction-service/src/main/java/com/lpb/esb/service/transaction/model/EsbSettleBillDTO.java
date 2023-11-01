package com.lpb.esb.service.transaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbSettleBillDTO {
    private String confirmTrn;
    private String customerNo;
    private String trnBrn;
    private String trnDesc;
    private String transactionId;
    private EsbPartnerDTO partner;
    private EsbServiceDTO service;
    private EsbBillInfoDTO billInfo;
    private EsbPostInfoDTO postInfo;
    private EsbAccountInfoDTO accountInfo;
}
