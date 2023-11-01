package com.lpb.insurance.dto.request.homeinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.insurance.dto.request.CustomerDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeInsuranceDTO {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("RefOfficerCode")
    private String refOfficerCode;
    @JsonProperty("RefOfficerName")
    private String refOfficerName;
    @JsonProperty("HouseAddress")
    private String houseAddress;
    @JsonProperty("Customer")
    private CustomerDTO customer;
    @JsonProperty("MoreInfo")
    private List<MoreInfoDTO> moreInfo;
    @JsonProperty("PrivateHouseFireInf")
    private PrivateHouseFireInfDTO privateHouseFireInf;
}
