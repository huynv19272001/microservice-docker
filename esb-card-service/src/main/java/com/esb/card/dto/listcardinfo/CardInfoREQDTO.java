package com.esb.card.dto.listcardinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "CardInfoRequest")
public class CardInfoREQDTO {
    @JsonProperty("clientCode")
    @JacksonXmlProperty(localName = "ClientCode")
    private String clientCode;
    @JsonProperty("cardID")
    @JacksonXmlProperty(localName = "CardID")
    private String cardID;
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "CardNumber")
    private String cardNumber;
    @JsonProperty("cmnd")
    @JacksonXmlProperty(localName = "CMND")
    private String cmnd;
    @JsonProperty("passport")
    @JacksonXmlProperty(localName = "PASSPORT")
    private String passport;
    @JsonProperty("inputType")
    @JacksonXmlProperty(localName = "InputType")
    private String inputType;
    @JsonProperty("appId")
    private String appId;
}
