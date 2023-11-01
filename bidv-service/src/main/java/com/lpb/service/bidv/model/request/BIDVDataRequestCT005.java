package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataRequestCT005 {
    @JsonProperty("mtid")
    private String mtId;
}
