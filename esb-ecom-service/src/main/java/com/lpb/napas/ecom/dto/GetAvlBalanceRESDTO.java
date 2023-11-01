package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
