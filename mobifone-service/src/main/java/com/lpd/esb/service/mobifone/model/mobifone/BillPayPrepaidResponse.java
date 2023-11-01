package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BillPayPrepaidResponse {
    private String phoneNumber;
    private String amount;
    private String result;
}
