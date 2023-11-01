package com.lpb.insurance.dto.request.healthinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditContractInfoDTO {
    @JsonProperty("LoanType")
    private String loanType;
    @JsonProperty("LoanProd")
    private String loanProd;
    @JsonProperty("CreditContract")
    private String creditContract;
    @JsonProperty("DisbursementDate")
    private String disbursementDate;
    @JsonProperty("ExpireDate")
    private String expireDate;
    @JsonProperty("LoanMoney")
    private int loanMoney;
    @JsonProperty("InsMoney")
    private int insMoney;
    @JsonProperty("EffectDate")
    private String effectDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("CustomerInsCollection")
    private List<CustomerInsCollectionDTO> customerInsCollection;
}
