package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyPaymentResponse {
    @JsonProperty("partnerTransId")
    private String partnerTransId;
    @JsonProperty("requestorTransId")
    private String requestorTransId;
    @JsonProperty("data")
    private String data;
    @JsonProperty("signature")
    private String signature;
}
