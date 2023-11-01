package com.esb.card.dto.canceldpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "CancelDppResponse")
public class CancelDppRESDTO {
    @JsonProperty("requestCode")
    private String requestCode;
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("operId")
    @JacksonXmlProperty(localName = "OperId")
    private String operId;
}
