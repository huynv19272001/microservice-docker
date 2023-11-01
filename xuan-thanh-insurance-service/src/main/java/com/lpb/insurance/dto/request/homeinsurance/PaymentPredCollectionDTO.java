package com.lpb.insurance.dto.request.homeinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPredCollectionDTO {
    @JsonProperty("Serial")
    private int serial;
    @JsonProperty("PaymentDate")
    private String paymentDate;
    @JsonProperty("Payment")
    private double payment;
}
