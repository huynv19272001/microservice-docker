package com.esb.card.dto.getlistdpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "GetListDppRequest")
public class GetListDPPREQDTO {
    @JsonProperty("typeInput")
    @JacksonXmlProperty(localName = "TypeInput")
    private String typeInput;
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("clientRequestTime")
    private String clientRequestTime;
    @JsonProperty("user")
    private String user;
    @JsonProperty("channel")
    private String channel;
}
