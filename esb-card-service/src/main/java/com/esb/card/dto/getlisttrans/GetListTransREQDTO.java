package com.esb.card.dto.getlisttrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "GetListTransRequest")
public class GetListTransREQDTO {
    @JsonProperty("typeInput")
    @JacksonXmlProperty(localName = "TypeInput")
    private String typeInput;
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("fromDate")
    @JacksonXmlProperty(localName = "FromDate")
    private String fromDate;
    @JsonProperty("toDate")
    @JacksonXmlProperty(localName = "ToDate")
    private String toDate;
    @JsonProperty("minAmount")
    @JacksonXmlProperty(localName = "MinAmount")
    private String minAmount;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("clientRequestTime")
    private String clientRequestTime;
    @JsonProperty("user")
    private String user;
    @JsonProperty("channel")
    private String channel;
}
