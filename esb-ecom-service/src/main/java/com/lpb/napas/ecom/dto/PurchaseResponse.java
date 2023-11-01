package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {
    @JsonProperty("partnerTransId")
    private String partnerTransId;
    @JsonProperty("requestorTransId")
    private String requestorTransId;
    private String data;
    @JsonProperty("signature")
    private String signature;
}
