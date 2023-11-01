package com.lpd.esb.service.mobifone.model.mobifone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BillInfoShortResponse {
    private String phoneNumber;
    private String custCode;
    private String debtStaCycle;
    private String usageCharge;
    private String payment;
    @JsonProperty("l_debt_remain")
    private String lDebtRemain;
    private String debtMonth;
    private String centerCode;
    private String redStatus;
}
