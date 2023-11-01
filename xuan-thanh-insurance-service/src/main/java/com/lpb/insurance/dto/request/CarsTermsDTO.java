package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarsTermsDTO {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("AccidentInsType")
    private String accidentInsType;
    @JsonProperty("EffectiveDate")
    private String effectiveDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Value")
    private String value;
    @JsonProperty("InsMoney")
    private String insMoney;
    @JsonProperty("PeopleNumb")
    private String peopleNumb;
    @JsonProperty("Fee")
    private String fee;
}
