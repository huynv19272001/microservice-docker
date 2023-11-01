package com.esb.card.dto.unif.updatecardstatus;

import com.esb.card.dto.unif.customerinfo.ManagementInfoDTO;
import com.esb.card.dto.unif.customerinfo.PersonInfoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "UpdateCardStatusRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCardStatusREQDTO {
    @JsonProperty("msgId")
    @JacksonXmlProperty(localName = "msgId")
    private String msgId;
    @JsonProperty("action")
    @JacksonXmlProperty(localName = "Action")
    private String action;
    @JsonProperty("cardNumber")
    @JacksonXmlProperty(localName = "CardNumber")
    private String cardNumber;
    @JsonProperty("cardStatus")
    @JacksonXmlProperty(localName = "CardStatus")
    private String cardStatus;
    @JsonProperty("reason")
    @JacksonXmlProperty(localName = "Reason")
    private String reason;
    @JsonProperty("note")
    @JacksonXmlProperty(localName = "Note")
    private String note;
}
