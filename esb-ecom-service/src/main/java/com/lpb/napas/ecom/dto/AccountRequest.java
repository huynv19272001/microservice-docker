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
public class AccountRequest {
    private String number;
    private String name;
    private String type;
    @JsonProperty("personalId")
    private String personalId;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    private String email;
    @JsonProperty("ibUsername")
    private String ibUsername;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("hardToken")
    private String hardToken;
    @JsonProperty("customerCode")
    private String customerCode;
}
