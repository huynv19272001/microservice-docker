package com.esb.card.dto.getlistdpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperDppRESDTO {
    @JsonProperty("operId")
    @JacksonXmlProperty(localName = "OperId")
    private String operId;
    @JsonProperty("operDate")
    @JacksonXmlProperty(localName = "OperDate")
    private String operDate;
    @JsonProperty("amountTotal")
    @JacksonXmlProperty(localName = "AmountTotal")
    private String amountTotal;
    @JsonProperty("installCount")
    @JacksonXmlProperty(localName = "InstallCount")
    private String installCount;
    @JsonProperty("installStartDate")
    @JacksonXmlProperty(localName = "InstallStartDate")
    private String installStartDate;
    @JsonProperty("installEndDate")
    @JacksonXmlProperty(localName = "InstallEndDate")
    private String installEndDate;
    @JsonProperty("amountPaid")
    @JacksonXmlProperty(localName = "AmountPaid")
    private String amountPaid;
    @JsonProperty("amountRemain")
    @JacksonXmlProperty(localName = "AmountRemain")
    private String amountRemain;
    @JsonProperty("type")
    @JacksonXmlProperty(localName = "Type")
    private String type;
    @JsonProperty("authCode")
    @JacksonXmlProperty(localName = "AuthCode")
    private String authCode;
}
