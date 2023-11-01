package com.lpb.esb.service.common.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class PartnerAccountInfo {
    String accNo;
    String accBranch;
    String accDesc;
    String customerNo;
    String merchantId;
    String type;
}
