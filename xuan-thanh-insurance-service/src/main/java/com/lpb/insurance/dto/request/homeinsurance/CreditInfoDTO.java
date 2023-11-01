package com.lpb.insurance.dto.request.homeinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditInfoDTO {
    @JsonProperty("ContractNumb")
    private String ContractNumb;
    @JsonProperty("Surplus")
    private int Surplus;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;
}
