package com.lpb.esb.service.transaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbSettleAccountDTO {
    private String SettleAcNo;
    private String SettleAcBrn;
    private String SettleAcDesc;
    private String SettleCustomerNo;
    private String SettleMerchant;
}
