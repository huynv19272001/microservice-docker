package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeCreditInfoDTORequest {
    @JsonProperty("DisbursementDate")
    private String disbursementDate;
    @JsonProperty("ExpireDate")
    private String expireDate;
    @JsonProperty("LoanMoney")
    private String loanMoney;
    @JsonProperty("InsMoney")
    private Integer insMoney;
    @JsonProperty("EffectDate")
    private String effectDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("CustomerInsCollection")
    private List customerInsCollection;
}
