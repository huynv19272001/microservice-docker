package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHuyHopDong {
    @JsonProperty("dSoId")
    private long dSoId;
    @JsonProperty("sSoHD")
    private String sSoHD;
}
