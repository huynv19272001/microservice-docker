package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditProductDetailDTO {
    @JsonProperty("DeadLine")
    private String deadLine;
    @JsonProperty("ProductCode")
    private String productCode;
    @JsonProperty("PaymentPredCollection")
    private List<PaymentPredDTO> paymentPredCollection;
    @JsonProperty("TermsCollection")
    private String termsCollection;
    @JsonProperty("AddTermsCollection")
    private String addTermsCollection;
}
