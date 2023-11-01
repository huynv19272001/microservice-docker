package com.esb.card.dto.availinfocard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailInfoCardRESDTO {
    @JsonProperty("CardId")
    private String CardId;
    @JsonProperty("CardMask")
    private String CardMask;
    @JsonProperty("CardNumber")
    private String CardNumber;
    @JsonProperty("EmbossedName")
    private String EmbossedName;
    @JsonProperty("CardStatus")
    private String CardStatus;
    @JsonProperty("BranchCode")
    private String BranchCode;
    @JsonProperty("CardType")
    private String CardType;
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
    @JsonProperty("Cardholdernamer")
    private String Cardholdernamer;
    @JsonProperty("IDType")
    private String IDType;
    @JsonProperty("IDNumber")
    private String IDNumber;
    @JsonProperty("Cif")
    private String Cif;
    @JsonProperty("MobileNumber")
    private String MobileNumber;
}
