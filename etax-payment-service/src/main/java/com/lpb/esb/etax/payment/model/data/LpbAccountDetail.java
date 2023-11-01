/**
 * @author Trung.Nguyen
 * @date 29-Sep-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbAccountDetail {

    @JsonProperty("BranchCode")
    private String branchCode;

    @JsonProperty("BranchName")
    private String branchName;

    @JsonProperty("AccountNo")
    private String accountNo;

    @JsonProperty("AccountCCY")
    private String accountCCY;

    @JsonProperty("AccountClass")
    private String accountClass;

    @JsonProperty("AccountClassDesc")
    private String accountClassDesc;

    @JsonProperty("RecordStat")
    private String recordStat;

    @JsonProperty("AuthStat")
    private String authStat;

    @JsonProperty("AccountDesc")
    private String accountDesc;

    @JsonProperty("AccountOpenDate")
    private String accountOpenDate;

    @JsonProperty("CIFNo")
    private String cifNo;

    @JsonProperty("CIFRecordStat")
    private String cifRecordStat;

    @JsonProperty("CIFAuthStat")
    private String cifAuthStat;

    @JsonProperty("PhoneNo")
    private String phoneNo;

    @JsonProperty("UniqueID")
    private String uniqueID;

    @JsonProperty("UniqueValue")
    private String uniqueValue;

    @JsonProperty("IssueDate")
    private String issueDate;

    @JsonProperty("IssuePlace")
    private String issuePlace;

    @JsonProperty("CurrentBalance")
    private String currentBalance;

    @JsonProperty("AvailableBalance")
    private String availableBalance;

    @JsonProperty("OpeningBalance")
    private String openingBalance;

    @JsonProperty("MinBalanceChecked")
    private String minBalanceChecked;

    @JsonProperty("MinBalance")
    private String minBalance;

    @JsonProperty("BlockedAmount")
    private String blockedAmount;

    @JsonProperty("CanDebit")
    private String canDebit;

    @JsonProperty("CanCredit")
    private String canCredit;

    @JsonProperty("CanPost")
    private String canPost;

    @JsonProperty("IsBlocked")
    private String isBlocked;

    @JsonProperty("IsFrozen")
    private String isFrozen;

    @JsonProperty("TotalLimit")
    private String totalLimit;

    @JsonProperty("LimitStartDate")
    private String limitStartDate;

    @JsonProperty("LimitEndDate")
    private String limitEndDate;

    @JsonProperty("SystemDate")
    private String systemDate;

    @JsonProperty("MakerID")
    private String makerID;

    @JsonProperty("MakerStamp")
    private String makerStamp;

    @JsonProperty("CheckerID")
    private String checkerID;

    @JsonProperty("CheckerStamp")
    private String checkerStamp;

    @JsonProperty("AccountName")
    private String accountName;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("SystemID")
    private String systemID;

    @JsonProperty("LineCode")
    private String lineCode;

    @JsonProperty("Note")
    private String note;

    @JsonProperty("Channel")
    private String channel;

}
