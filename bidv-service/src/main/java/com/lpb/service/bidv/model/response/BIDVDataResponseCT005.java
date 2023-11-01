package com.lpb.service.bidv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataResponseCT005 {
    @JsonProperty("mtid")
    private String mtId;
    @JsonProperty("err_code")
    private String errorCode;
    @JsonProperty("err_desc")
    private String errorDesc;
}
