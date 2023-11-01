package com.lpb.insurance.dto.request.healthinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.insurance.dto.request.CustomerDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthInsuranceDTO {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("CreditContractInfo")
    private CreditContractInfoDTO refOfficerCode;
    @JsonProperty("Customer")
    private CustomerDTO Customer;
}
