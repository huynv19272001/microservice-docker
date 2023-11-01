package com.lpb.esb.service.query.model;

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
