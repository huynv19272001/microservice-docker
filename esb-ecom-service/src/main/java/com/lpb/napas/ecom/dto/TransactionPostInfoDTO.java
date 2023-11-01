package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionPostInfoDTO {
    private String trnBranch;
    private String trnDesc;
    private String accBranch;
    private String lcyAmount;
    private String accNo;
    private String accCcy;
    private String drcrInd;
    private String relatedCustomer;
}
