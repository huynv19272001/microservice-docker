package com.esb.card.dto.getfeedpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "GetFeeDppResponse")
public class GetFeeDPPRESDTO {
    @JsonProperty("requestCode")
    private String requestCode;
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardID")
    private String cardId;
    @JsonProperty("installmentTerm")
    @JacksonXmlProperty(localName = "InstallmentTerm")
    private String installmentTerm;
    @JsonProperty("fee")
    @JacksonXmlProperty(localName = "Fee")
    private String fee;
    @JsonProperty("amountFee")
    @JacksonXmlProperty(localName = "AmountFee")
    private String amountFee;
    @JsonProperty("expectAmount")
    @JacksonXmlProperty(localName = "ExpectAmount")
    private String expectAmount;
}
