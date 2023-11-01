package com.esb.card.dto.getavlbalance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAvlBalanceRESDTO {
    private String branchCode;
    private String accountNumber;
    private String accountName;
    private String customerNumber;
    private String accountCcy;
    private String accountClass;
    private String acStatNoDr;
    private String acStatNoCr;
    private String minBalance;
    private String acyAvlBal;
    private String accountType;
    private String drAccrued;
    private String crAccrued;
}
