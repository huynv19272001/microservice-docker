package com.esb.card.dto.cardtranlimit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCardTranLimitRESDTO {
    @JsonProperty("channel")
    @JacksonXmlProperty(localName = "Channel")
    private String channel;
    @JsonProperty("tranLimitDay")
    @JacksonXmlProperty(localName = "TranLimitDay")
    private String tranLimitDay;
    @JsonProperty("nuOfTranDay")
    @JacksonXmlProperty(localName = "NuOfTranDay")
    private String nuOfTranDay;
    @JsonProperty("maxTranLimitDay")
    @JacksonXmlProperty(localName = "MaxTranLimitDay")
    private String maxTranLimitDay;
    @JsonProperty("maxNuOfTranDay")
    @JacksonXmlProperty(localName = "MaxNuOfTranDay")
    private String maxNuOfTranDay;
    @JsonProperty("amountPerTrans")
    @JacksonXmlProperty(localName = "AmountPerTrans")
    private String amountPerTrans;
    @JsonProperty("maxAmountPerTrans")
    @JacksonXmlProperty(localName = "MaxAmountPerTrans")
    private String maxAmountPerTrans;
}
