package com.lpb.service.bidv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataResponseCT006 {
    @JsonProperty("mtid")
    private String mtId;
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("message")
    private String message;
}
