package com.esb.card.dto.creditcardinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "CreditCardInfoResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardInfoRESDTO {
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("cardMask")
    @JacksonXmlProperty(localName = "CardMask")
    private String cardMask;
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "CardNumber")
    private String cardNumber;
    @JsonProperty("embossedName")
    @JacksonXmlProperty(localName = "EmbossedName")
    private String embossedName;
    @JsonProperty("cardStatus")
    @JacksonXmlProperty(localName = "CardStatus")
    private String cardStatus;
    @JsonProperty("branchCode")
    @JacksonXmlProperty(localName = "BranchCode")
    private String branchCode;
    @JsonProperty("basicCardFlag")
    @JacksonXmlProperty(localName = "BasicCardFlag")
    private String basicCardFlag;
    @JsonProperty("creditAccount")
    @JacksonXmlProperty(localName = "CreditAccount")
    private String creditAccount;
    @JsonProperty("basicPhoneNumber")
    @JacksonXmlProperty(localName = "BasicPhoneNumber")
    private String basicPhoneNumber;
    @JsonProperty("accountCreditLimit")
    @JacksonXmlProperty(localName = "AccountCreditLimit")
    private String accountCreditLimit;
    @JsonProperty("accountCashLimit")
    @JacksonXmlProperty(localName = "AccountCashLimit")
    private String accountCashLimit;
    @JsonProperty("accountAvailCredit")
    @JacksonXmlProperty(localName = "AccountAvailCredit")
    private String accountAvailCredit;
    @JsonProperty("accountAvailCash")
    @JacksonXmlProperty(localName = "AccountAvailCash")
    private String accountAvailCash;
    @JsonProperty("creditLimit")
    @JacksonXmlProperty(localName = "CreditLimit")
    private String creditLimit;
    @JsonProperty("cashLimit")
    @JacksonXmlProperty(localName = "CashLimit")
    private String cashLimit;
    @JsonProperty("cardAvailCredit")
    @JacksonXmlProperty(localName = "CardAvailCredit")
    private String cardAvailCredit;
    @JsonProperty("cardAvailCash")
    @JacksonXmlProperty(localName = "CardAvailCash")
    private String cardAvailCash;
    @JsonProperty("totalOutStanding")
    @JacksonXmlProperty(localName = "TotalOutStanding")
    private String totalOutStanding;
    @JsonProperty("lastStatementDate")
    @JacksonXmlProperty(localName = "LastStatementDate")
    private String lastStatementDate;
    @JsonProperty("lastDUEdate")
    @JacksonXmlProperty(localName = "LastDUEdate")
    private String lastDUEdate;
    @JsonProperty("lastStatOutStanding")
    @JacksonXmlProperty(localName = "LastStatOutStanding")
    private String lastStatOutStanding;
    @JsonProperty("lastMinDue")
    @JacksonXmlProperty(localName = "LastMinDue")
    private String lastMinDue;
    @JsonProperty("clientCode")
    @JacksonXmlProperty(localName = "ClientCode")
    private String clientCode;
    @JsonProperty("cardType")
    @JacksonXmlProperty(localName = "CardType")
    private String cardType;
    @JsonProperty("totalStatementPayment")
    @JacksonXmlProperty(localName = "TotalStatementPayment")
    private String totalStatementPayment;
    @JsonProperty("availableBalance")
    @JacksonXmlProperty(localName = "AvailableBalance")
    private String availableBalance;
}
