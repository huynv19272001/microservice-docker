package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsDTO {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("BusinessType")
    private String businessType;
    @JsonProperty("AccidentInsType")
    private String accidentInsType;
    @JsonProperty("EffectiveDate")
    private String effectiveDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Value")
    private String value;
    @JsonProperty("PeopleNumb")
    private String peopleNumb;
    @JsonProperty("Tonnage")
    private String tonnage;
    @JsonProperty("InsMoney")
    private String insMoney;
    @JsonProperty("Percent")
    private String percent;
    @JsonProperty("Tax")
    private String tax;
    @JsonProperty("RevenueFee")
    private String revenueFee;
    @JsonProperty("PaymentFee")
    private String PaymentFee;
}
