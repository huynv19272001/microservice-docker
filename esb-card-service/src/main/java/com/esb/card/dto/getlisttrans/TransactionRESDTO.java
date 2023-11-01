package com.esb.card.dto.getlisttrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRESDTO {
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("operId")
    @JacksonXmlProperty(localName = "OperId")
    private String operId;
    @JsonProperty("operDate")
    @JacksonXmlProperty(localName = "OperDate")
    private String operDate;
    @JsonProperty("amount")
    @JacksonXmlProperty(localName = "Amount")
    private String amount;
    @JsonProperty("authCode")
    @JacksonXmlProperty(localName = "AuthCode")
    private String authCode;
    @JsonProperty("merchant")
    @JacksonXmlProperty(localName = "Merchant")
    private String merchant;
}
