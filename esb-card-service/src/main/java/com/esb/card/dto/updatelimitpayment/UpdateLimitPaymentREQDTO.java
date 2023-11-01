package com.esb.card.dto.updatelimitpayment;

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
@JacksonXmlRootElement(localName = "UpdateLimitPaymentRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateLimitPaymentREQDTO {
    @JsonProperty("cardId")
    @JacksonXmlProperty(localName = "CardId")
    private String cardId;
    @JsonProperty("payAmount")
    @JacksonXmlProperty(localName = "PayAmount")
    private String payAmount;
    @JsonProperty("msgId")
    @JacksonXmlProperty(localName = "MsgId")
    private String msgId;
    @JsonProperty("originatorRefnum")
    @JacksonXmlProperty(localName = "Originator_Refnum")
    private String originatorRefnum;
}
