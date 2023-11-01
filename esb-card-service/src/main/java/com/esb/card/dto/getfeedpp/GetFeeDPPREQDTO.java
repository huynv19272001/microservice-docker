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
@JacksonXmlRootElement(localName = "GetFeeDppRequest")
public class GetFeeDPPREQDTO {
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardID")
    private String cardId;
    @JsonProperty("operAmount")
    @JacksonXmlProperty(localName = "Oper_amount")
    private String operAmount;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("clientRequestTime")
    private String clientRequestTime;
    @JsonProperty("user")
    private String user;
    @JsonProperty("channel")
    private String channel;
}
