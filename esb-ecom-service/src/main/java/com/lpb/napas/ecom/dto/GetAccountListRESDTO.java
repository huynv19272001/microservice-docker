package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAccountListRESDTO {
    private String branchCode;
    private String accountNumber;
    private String accountName;
    private String customerNumber;
    private String accountCcy;
    private String accountClass;
    private String acStatNoDr;
    private String acStatNoCr;
    private String acStatDormant;
    private String acOpenDate;
    private String altAcNo;
    private String atmFacility;
    private String passbookFacility;
    private String acStatFrozen;
    private String todLimit;
    private String drGl;
    private String crGl;
    private String recordStat;
    private String authStat;
    private String modNo;
    private String makerId;
    private String minBalance;
}
