package com.esb.card.dto.listcardinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoDTO {
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
