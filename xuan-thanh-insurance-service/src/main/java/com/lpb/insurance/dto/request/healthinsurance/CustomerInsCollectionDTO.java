package com.lpb.insurance.dto.request.healthinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInsCollectionDTO {
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("DOB")
    private String dob;
    @JsonProperty("IdentifyCard")
    private String identifyCard;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("InsMoney")
    private int insMoney;
    @JsonProperty("Fee")
    private int fee;
}
