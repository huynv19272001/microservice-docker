package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVRequest<T> {
    @JsonProperty("app_code")
    private String appCode;
    @JsonProperty("request_date")
    private String requestDate;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("signature")
    private String signature;
    private List<T> data;
}
