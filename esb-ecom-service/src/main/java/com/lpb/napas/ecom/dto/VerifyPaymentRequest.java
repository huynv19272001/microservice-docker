package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyPaymentRequest {
    @JsonProperty("requestorCode")
    private String requestorCode;
    @JsonProperty("requestorPassword")
    private String requestorPassword;
    @JsonProperty("requestorTransId")
    private String requestorTransId;
    private String data;
    @JsonProperty("signature")
    private String signature;
}
