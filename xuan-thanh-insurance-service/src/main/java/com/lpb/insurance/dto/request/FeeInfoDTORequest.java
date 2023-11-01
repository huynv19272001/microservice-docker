package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeInfoDTORequest {
    @JsonProperty("HouseValue")
    private String houseValue;
    @JsonProperty("HouseInsVal")
    private String houseInsVal;
    @JsonProperty("AssetValue")
    private String assetValue;
    @JsonProperty("AssetInsVal")
    private Integer assetInsVal;
    @JsonProperty("HouseType")
    private String houseType;
    @JsonProperty("EffectDate")
    private String effectDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("PaymentPredCollection")
    private List<PaymentPredDTO> paymentPredCollection;
}
