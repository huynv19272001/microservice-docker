package com.esb.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRESHeaderDTO {
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("ResponseTranDate")
    private String responseTranDate;
    @JsonProperty("ResponseDateTime")
    private String responseDateTime;
    @JsonProperty("FunctionCode")
    private String functionCode;
    @JsonProperty("ChannelCode")
    private String channelCode;
    @JsonProperty("HttpStatusCode")
    private String httpStatusCode;
    @JsonProperty("ResponseDesc")
    private String responseDesc;
}
