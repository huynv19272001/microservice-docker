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
public class MerchantRequest {
    private String id;
    private String code;
    private String name;
    @JsonProperty("categoryCode")
    private String categoryCode;
    @JsonProperty("addInfo")
    private String addInfo;
}
