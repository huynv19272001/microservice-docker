/**
 * @author Trung.Nguyen
 * @date 06-Sep-2022
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
public class LpbCard {

    @JsonProperty("CardId")
    private String cardId;

    @JsonProperty("CardMask")
    private String cardMask;

    @JsonProperty("CardNumber")
    private String cardNumber;

    @JsonProperty("EmbossedName")
    private String embossedName;

    @JsonProperty("CardStatus")
    private String cardStatus;

    @JsonProperty("BranchCode")
    private String branchCode;

    @JsonProperty("ExpirationDate")
    private String expirationDate;

    @JsonProperty("AccountNumber")
    private String accountNumber;

    @JsonProperty("CardType")
    private String cardType;

    @JsonProperty("ClientCode")
    private String clientCode;

    @JsonProperty("BasicCardFlag")
    private String basicCardFlag;

    @JsonProperty("CustomerNumber")
    private String customerNumber;

    @JsonProperty("Ecom")
    private String ecom;

    @JsonProperty("EcomDateFrom")
    private String ecomDateFrom;

    @JsonProperty("EcomDateTo")
    private String ecomDateTo;

    @JsonProperty("Overs")
    private String overs;

    @JsonProperty("OversDateFrom")
    private String oversDateFrom;

    @JsonProperty("OversDateTo")
    private String oversDateTo;

    @JsonProperty("PhoneEpin")
    private String phoneEpin;

    @JsonProperty("BasicCardId")
    private String basicCardId;

    @JsonProperty("PinCount")
    private String pinCount;

}
