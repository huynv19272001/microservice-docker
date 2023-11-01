package com.lpb.esb.service.pti.model.pti.privatehouse.tinhphi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Data {
    @NotBlank
    @JsonProperty("NGAY_HT")
    private String ngayHt;
    @JsonProperty("PT")
    private double pt;
    @NotBlank
    private String lhNv;
    @NotBlank
    private String dtBh;
    @NotBlank
    private String mdSd;
    @NotBlank
    private String tienTs;
    @NotBlank
    private String tienTncc;
    @NotBlank
    private String ntTien;
    @NotBlank
    private String ntPhi;
    @JsonProperty("TIEN_BH")
    private double tienBh;
    private int soThangBh;
    private int soNgayBh;
    @NotBlank
    private String htTra;
    @NotBlank
    @JsonProperty("NGAY_HL")
    private String ngayHl;
    @NotBlank
    @JsonProperty("NGAY_KT")
    private String ngayKt;
    @NotBlank
    private String htTtoan;
    @JsonProperty("GTRI_TS")
    private double gtriTs;
}
