package com.lpb.esb.service.pti.model.pti.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DsDk {
    private String tt;
    @JsonProperty("MA_DKMR")
    private String maDkmr;
    private String pt;
    private String phi;
    private String tenT;
    private String ndDkmr;
    private String kieuNh;
}
