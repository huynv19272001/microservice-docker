package com.lpb.esb.service.common.model.response;

import lombok.*;

/**
 * Created by tudv1 on 2022-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class SettleAccountInfo {
    String accNo;
    String accBranch;
    String accDesc;
    String customerNo;
    String merchantId;
}
