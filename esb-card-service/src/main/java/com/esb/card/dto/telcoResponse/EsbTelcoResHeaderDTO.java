package com.esb.card.dto.telcoResponse;

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
public class EsbTelcoResHeaderDTO {
    @JsonProperty("ChannelCode")
    private String channelCode;
    @JsonProperty("FunctionCode")
    private String functionCode;
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("ResponseTranDate")
    private String responseTranDate;
    @JsonProperty("ResponseDateTime")
    private String requestDateTime;
    @JsonProperty("HttpStatusCode")
    private Integer httpStatusCode;
    @JsonProperty("ResponseDesc")
    private String ResponseDesc;
}
