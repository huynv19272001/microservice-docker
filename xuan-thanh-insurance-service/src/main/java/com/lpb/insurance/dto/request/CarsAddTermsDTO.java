package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarsAddTermsDTO {
    @JsonProperty("Code")
    private String code;
    @JsonProperty("InsMoney")
    private String insMoney;
    @JsonProperty("Percent")
    private String percent;
    @JsonProperty("RevenueFee")
    private String revenueFee;
    @JsonProperty("Tax")
    private String tax;
    @JsonProperty("PaymentFee")
    private String paymentFee;

}
