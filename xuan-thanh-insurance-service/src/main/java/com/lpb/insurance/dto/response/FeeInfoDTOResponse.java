package com.lpb.insurance.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.insurance.dto.request.ProductDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeInfoDTOResponse {
    @JsonProperty("InsMoney")
    private String insMoney;
    @JsonProperty("RevenueFee")
    private String revenueFee;
    @JsonProperty("FeePerc")
    private String feePerc;
    @JsonProperty("Tax")
    private String tax;
    @JsonProperty("TaxPerc")
    private String taxPerc;
    @JsonProperty("PaymentFee")
    private String paymentFee;
    @JsonProperty("ForCtrlCode")
    private String forCtrlCode;
    @JsonProperty("ContractType")
    private String contractType;
    @JsonProperty("ProductDetail")
    private ProductDetailDTO productDetail;
}
