package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BillPayPostpaidResponse {
    private String phoneNumber;
    private String custCode;
    private String settlementAmount;
    private String debtStaCycle;
    private String usageCharge;
    private String payment;
    private String paymentId;
    private String paymentStartDate;
    private String paymentEndDate;
    private String centerCode;
    private String billCycleID;
}
