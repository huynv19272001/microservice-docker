package com.lpb.service.bidv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataResponseCT003 {
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("status")
    private String status;
    @JsonProperty("web_status")
    private String webStatus;
    @JsonProperty("web_process_status")
    private String webProcessStatus;
    @JsonProperty("mtid")
    private String mtId;
    @JsonProperty("message")
    private String message;
}
