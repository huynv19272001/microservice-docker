package com.esb.card.dto.updatelimitpayment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "UpdateLimitPaymentResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateLimitPaymentRESDTO {
    @JsonProperty("customerDebt")
    @JacksonXmlProperty(localName = "Customer_Debt")
    private String customerDebt;
    @JsonProperty("accountNumber")
    @JacksonXmlProperty(localName = "Account_Number")
    private String accountNumber;
    @JsonProperty("authId")
    @JacksonXmlProperty(localName = "AuthId")
    private String authId;
    @JsonProperty("availCredit")
    @JacksonXmlProperty(localName = "AvailCredit")
    private String availCredit;
    @JsonProperty("refNum")
    @JacksonXmlProperty(localName = "RefNum")
    private String refNum;
    @JsonProperty("operDate")
    @JacksonXmlProperty(localName = "Oper_date")
    private String operDate;
    @JsonProperty("hostDate")
    @JacksonXmlProperty(localName = "Host_date")
    private String hostDate;
}
