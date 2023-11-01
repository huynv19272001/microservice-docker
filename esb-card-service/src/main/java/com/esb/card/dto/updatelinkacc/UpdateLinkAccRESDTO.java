package com.esb.card.dto.updatelinkacc;

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
@JacksonXmlRootElement(localName = "UpdateLinkAccResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateLinkAccRESDTO {
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "CardNumber")
    private String cardNumber;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("basicCardFlag")
    @JacksonXmlProperty(localName = "BasicCardFlag")
    private String basicCardFlag;
    @JsonProperty("embossedName")
    @JacksonXmlProperty(localName = "EmbossedName")
    private String embossedName;
    @JsonProperty("resultStatus")
    @JacksonXmlProperty(localName = "ResultStatus")
    private ResultStatusRESDTO resultStatusRESDTO;
    @JsonProperty("data")
    @JacksonXmlProperty(localName = "Data")
    private DataRESDTO dataRESDTO;
}
