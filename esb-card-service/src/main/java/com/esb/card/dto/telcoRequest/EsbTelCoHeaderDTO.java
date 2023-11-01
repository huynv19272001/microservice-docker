package com.esb.card.dto.telcoRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by cuongnm on 2022-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbTelCoHeaderDTO {
    @JsonProperty("ChannelCode")
    private String channelCode;
    @JsonProperty("FunctionCode")
    private String funcionCode;
    @JsonProperty("RequestCode")
    private String requestCode;
    @JsonProperty("RequestTranDate")
    private String requestTranDate;
    @JsonProperty("RequestDateTime")
    private String requestDateTime;
    @JsonProperty("HttpStatusCode")
    private String httpStatusCode;
    @JsonProperty("ResponseDesc")
    private String ResponseDesc;
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("Password")
    private String passWord;
}

