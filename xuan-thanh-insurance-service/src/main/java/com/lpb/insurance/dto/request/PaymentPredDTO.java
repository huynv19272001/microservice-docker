package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPredDTO {
    @JsonProperty("Serial")
    private String serial;
    @JsonProperty("PaymentDate")
    private String paymentDate;
    @JsonProperty("Payment")
    private String payment;
    @JsonProperty("InsMoney")
    private String insMoney;
}
