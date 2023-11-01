package com.esb.card.dto.creditcardinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardInfoREQDTO {
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("inputType")
    private String inputType;
    @JsonProperty("msgId")
    private String msgId;
}
