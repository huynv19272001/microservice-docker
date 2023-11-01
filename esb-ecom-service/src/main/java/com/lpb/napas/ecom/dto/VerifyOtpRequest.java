package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequest {
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
