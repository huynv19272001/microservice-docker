package com.esb.transaction.dto;

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
    private String relatedCustomer;
    private String drcrInd;
    private String accCcy;
}
