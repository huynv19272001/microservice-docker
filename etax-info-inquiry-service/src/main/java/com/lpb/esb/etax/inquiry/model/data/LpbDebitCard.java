/**
 * @author Trung.Nguyen
 * @date 07-Jun-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbDebitCard {

	@JsonProperty("CardId")
    private String cardId;

    @JsonProperty("CardMask")
    private String cardMask;

    @JsonProperty("CardNumber")
    private String cardNumber;

    @JsonProperty("EmbossedName")
    private String embossedName;

    @JsonProperty("BranchCode")
    private String branchCode;

    @JsonProperty("CardType")
    private String cardType;

    @JsonProperty("CardStatus")
    private String cardStatus;

    @JsonProperty("AccountNumber")
    private String accountNumber;

    @JsonProperty("Identification")
    private String identification;

    @JsonProperty("AvailableBalance")
    private String availableBalance;

    @JsonProperty("IssueDate")
    private String issueDate;

    @JsonProperty("BasicCardFlag")
    private String basicCardFlag;

    @JsonProperty("BasicPhoneNumber")
    private String basicPhoneNumber;

}
