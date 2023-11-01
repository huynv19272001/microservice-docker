package com.esb.card.dto.updatelinkacc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "UpdateLinkAccRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateLinkAccREQDTO {
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "CardNumber")
    private String cardNumber;
    @JsonProperty("action")
    @JacksonXmlProperty(localName = "Action")
    private String action;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("basicCardFlag")
    @JacksonXmlProperty(localName = "BasicCardFlag")
    private String basicCardFlag;
    @JsonProperty("listChangeAccount")
    @JacksonXmlProperty(localName = "ListChangeAccount")
    private List<ChangeAccountREQDTO> listChangeAccountREQDTO;
    @JsonProperty("msgId")
    private String msgId;
}
