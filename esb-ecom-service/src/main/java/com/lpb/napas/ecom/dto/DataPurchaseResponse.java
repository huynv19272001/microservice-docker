package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPurchaseResponse {
    @JsonProperty("approvedCode")
    private String approvedCode;
    @JsonProperty("resCode")
    private String resCode;
    @JsonProperty("resMessage")
    private String resMessage;
    @JsonProperty("otherInfo")
    private List<OtherInfoRequest> otherInfo;
}
