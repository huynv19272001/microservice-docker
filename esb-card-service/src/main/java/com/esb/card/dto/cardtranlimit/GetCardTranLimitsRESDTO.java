package com.esb.card.dto.cardtranlimit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCardTranLimitsRESDTO {
    @JacksonXmlProperty(localName = "GetCardTranLimit")
    @JsonProperty("getCardTranLimit")
    private List<GetCardTranLimitRESDTO> channel;
}
