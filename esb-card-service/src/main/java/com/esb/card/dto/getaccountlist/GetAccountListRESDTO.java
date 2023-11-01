package com.esb.card.dto.getaccountlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
