package com.lpb.esb.service.pti.model.pti.car;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InDon {
    private String maBc;
    private String nv;
    private String dviSl;
    private String soId;
    private String soIdDt;
    private String loaiIn;
    private String api;
}
