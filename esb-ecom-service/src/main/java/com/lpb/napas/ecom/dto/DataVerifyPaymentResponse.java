package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.napas.ecom.dto.OtherInfoRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataVerifyPaymentResponse {
    @JsonProperty("approvedCode")
    private String approvedCode;
    @JsonProperty("resCode")
    private String resCode;
    @JsonProperty("resMessage")
    private String resMessage;
    @JsonProperty("redirectUrl")
    private String redirectUrl;
    @JsonProperty("otherInfo")
    private List<OtherInfoRequest> otherInfo;
}
