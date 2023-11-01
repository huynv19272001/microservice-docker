package com.lpb.insurance.dto.request.homeinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateHouseFireInfDTO {
    @JsonProperty("HouseValue")
    private int houseValue;
    @JsonProperty("HouseInsVal")
    private int houseInsVal;
    @JsonProperty("AssetValue")
    private int assetValue;
    @JsonProperty("AssetInsVal")
    private int assetInsVal;
    @JsonProperty("HouseType")
    private String houseType;
    @JsonProperty("EffectDate")
    private String effectDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("PaymentPredCollection")
    private List<PaymentPredCollectionDTO> paymentPredCollection;
}
