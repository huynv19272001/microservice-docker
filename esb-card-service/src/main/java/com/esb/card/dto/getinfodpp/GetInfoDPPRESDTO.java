package com.esb.card.dto.getinfodpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "GetInfoDppResponse")
public class GetInfoDPPRESDTO {
    @JsonProperty("requestCode")
    private String requestCode;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardID")
    private String cardId;
    @JsonProperty("operId")
    @JacksonXmlProperty(localName = "OperID")
    private String operId;
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "Card_Number")
    private String cardNumber;
    @JsonProperty("amountBilled")
    @JacksonXmlProperty(localName = "Amount_billed")
    private String amountBilled;
    @JsonProperty("operDate")
    @JacksonXmlProperty(localName = "Oper_date")
    private String operDate;
    @JsonProperty("operAmount")
    @JacksonXmlProperty(localName = "Oper_amount")
    private String operAmount;
    @JsonProperty("startDate")
    @JacksonXmlProperty(localName = "Start_date")
    private String startDate;
    @JsonProperty("endDate")
    @JacksonXmlProperty(localName = "End_date")
    private String endDate;
    @JsonProperty("operStatus")
    @JacksonXmlProperty(localName = "Oper_status")
    private String operStatus;
    @JsonProperty("installmentTerm")
    @JacksonXmlProperty(localName = "Installment_Term")
    private String installmentTerm;
    @JsonProperty("fee")
    @JacksonXmlProperty(localName = "Fee")
    private String fee;
    @JsonProperty("amountFee")
    @JacksonXmlProperty(localName = "Amount_Fee")
    private String amountFee;
    @JsonProperty("expectAmount")
    @JacksonXmlProperty(localName = "Expect_Amount")
    private String expectAmount;

}
