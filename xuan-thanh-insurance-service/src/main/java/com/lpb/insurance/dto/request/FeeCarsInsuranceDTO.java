package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeCarsInsuranceDTO {
    @JsonProperty("AutoMakers")
    private String autoMakers;
    @JsonProperty("TradeMark")
    private String tradeMark;
    @JsonProperty("ManufactureYear")
    private String manufactureYear;
    @JsonProperty("CarUsagePattern")
    private String carUsagePattern;
    @JsonProperty("CarType")
    private String carType;
    @JsonProperty("SeatNumbs")
    private String seatNumbs;
    @JsonProperty("Tonnage")
    private String tonnage;
    @JsonProperty("EffectiveDate")
    private String effectiveDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Value")
    private String value;
    @JsonProperty("InsMoney")
    private String insMoney;
    @JsonProperty("TermsCollection")
    private List<CarsTermsDTO> termsCollection;
    @JsonProperty("AddTermsCollection")
    private List<AddTermsDTO> addTermsCollection;
}
