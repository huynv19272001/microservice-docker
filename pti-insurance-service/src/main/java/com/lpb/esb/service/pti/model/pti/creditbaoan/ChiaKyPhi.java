package com.lpb.esb.service.pti.model.pti.creditbaoan;

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
    private String htHdKenh;
    private String htTtoan;
    private String kieuHd;
    private String soHdGoc;
    private String ttoan;
    private String phi;
    private String pt;
    private String tienBh;
    private String ngayHt;
    private String ngayCap;
    private String soThangBh;
    private String soNgayBh;
}
