package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BillPayCancelResponse {
    private String phoneNumber;
    private String abortAmount;
    private String debtRemain;
    private String centerCode;
}
