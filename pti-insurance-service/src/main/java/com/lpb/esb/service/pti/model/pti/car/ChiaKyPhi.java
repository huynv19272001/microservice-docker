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
public class ChiaKyPhi {
    private String ngayHl;
    private String htTra;
    private String ttoan;
    private String phi;
    private String phiTnds;
    private String ngayHt;
    private String ngayCap;
    private String kieuHd;
    private String soHdG;
}
