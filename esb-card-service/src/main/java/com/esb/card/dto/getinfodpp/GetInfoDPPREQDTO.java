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
@JacksonXmlRootElement(localName = "GetInfoDppRequest")
public class GetInfoDPPREQDTO {
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardID")
    private String cardId;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("operId")
    @JacksonXmlProperty(localName = "OperID")
    private String operId;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("clientRequestTime")
    private String clientRequestTime;
    @JsonProperty("user")
    private String user;
    @JsonProperty("channel")
    private String channel;
}
