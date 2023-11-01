package com.lpb.esb.service.pti.model.pti.creditbaoan;

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
public class TinhPhi {
    @JsonProperty("NGAY_HT")
    private int ngayHt;
    @JsonProperty("HT_HD_KENH")
    private String htHdKenh;
    private String loaiVay;
    private int hanVayNgay;
    @JsonProperty("LOAI_BH")
    private String loaiBh;
    @JsonProperty("SO_THANG_BH")
    private int soThangBh;
    private int soNgayBh;
    @JsonProperty("TIEN_BH")
    private int tienBh;
    private String goi;
    private String ntVay;
    private double ptG;
    private int ptC;
    private double pt;
    @JsonProperty("PHI")
    private int phi;
    private int phiDv;
    private int ttoan;
    @JsonProperty("NGAY_HL")
    private String ngayHl;
    @JsonProperty("NGAY_KT")
    private String ngayKt;
}
