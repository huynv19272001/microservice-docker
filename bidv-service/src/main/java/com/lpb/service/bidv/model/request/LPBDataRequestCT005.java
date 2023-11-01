package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LPBDataRequestCT005 {
    @JsonProperty("MtId")
    private String mtId;
}
