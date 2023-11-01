package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardInfoRESDTO {
    @JsonProperty("CardId")
    private String CardId;
    @JsonProperty("CardMask")
    private String CardMask;
    @JsonProperty("CardNumber")
    private String CardNumber;
    @JsonProperty("EmbossedName")
    private String EmbossedName;
    @JsonProperty("BranchCode")
    private String BranchCode;
    @JsonProperty("CardType")
    private String CardType;
    @JsonProperty("CardStatus")
    private String CardStatus;
    @JsonProperty("AccountNumber")
    private String AccountNumber;
    @JsonProperty("Identification")
    private String Identification;
    @JsonProperty("AvailableBalance")
    private String AvailableBalance;
    @JsonProperty("IssueDate")
    private String IssueDate;
    @JsonProperty("BasicCardFlag")
    private String BasicCardFlag;
    @JsonProperty("BasicPhoneNumber")
    private String BasicPhoneNumber;
}
