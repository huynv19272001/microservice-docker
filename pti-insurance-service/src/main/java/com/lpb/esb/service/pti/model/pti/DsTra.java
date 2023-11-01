package com.lpb.esb.service.pti.model.pti;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DsTra {
    @JsonProperty("HT_TRA")
    private String htTra;
    @JsonProperty("NGAY")
    private String ngay;
    @JsonProperty("TIEN")
    private String tien;
}
