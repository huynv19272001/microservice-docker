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
public class TransactionRequest {
    private String trace;
    private String info;
    private String date;
    private String amount;
    private String currency;
    @JsonProperty("serviceType")
    private String serviceType;
    private String otp;
}
