package com.esb.card.dto.cardtranlimit;

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
@JacksonXmlRootElement(localName = "GetCardTranLimitResponse")
public class GetCardTranLimit {
    @JacksonXmlProperty(localName = "GetCardTranLimits")
    @JsonProperty("getCardTranLimits")
    GetCardTranLimitsRESDTO getCardTranLimitsRESDTO;
}
