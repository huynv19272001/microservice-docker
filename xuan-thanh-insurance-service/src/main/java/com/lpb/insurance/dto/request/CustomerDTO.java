package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.insurance.dto.request.homeinsurance.CreditInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("DOB")
    private String dob;
    @JsonProperty("IdentifyCard")
    private String identifyCard;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("CounterCode")
    private String counterCode;
    @JsonProperty("CreditInfo")
    private CreditInfoDTO creditInfo;
    @JsonProperty("RepresentativeCollection")
    private List<RepresentativeCollectionDTO> representativeCollection;
}
