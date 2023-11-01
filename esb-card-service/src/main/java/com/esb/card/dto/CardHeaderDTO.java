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
public class CardHeaderDTO {
    @JsonProperty("RequestCode")
    private String requestCode;
    @JsonProperty("RequestTranDate")
    private String requestTranDate;
    @JsonProperty("RequestDateTime")
    private String requestDateTime;
    @JsonProperty("FunctionCode")
    private String functionCode;
    @JsonProperty("ChannelCode")
    private String channelCode;
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("Password")
    private String password;
}
