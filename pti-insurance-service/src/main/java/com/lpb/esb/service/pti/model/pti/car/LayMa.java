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
public class LayMa {
    private String dviSl;
    private String soId;
    private String soIdDt;
    private String nv;
    private String loai;
    private String kieuHd;
}
