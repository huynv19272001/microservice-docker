package com.lpb.esb.service.pti.model.pti.creditbaoan.nhapdon;

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
public class Data {
    @JsonProperty("DVI_SL")
    private String dviSl;
    private String kieuHd;
    private String soHdG;
    @JsonProperty("NGAY_HT")
    private int ngayHt;
    private String ttrang;
    private String soHd;
    private String tenDn;
    private String maThue;
    private String soDkkd;
    private String dchiDn;
    @JsonProperty("TEN")
    private String ten;
    private String gioi;
    private String chucVu;
    @JsonProperty("NGAY_SINH")
    private int ngaySinh;
    @JsonProperty("SO_CMT")
    private String soCmt;
    @JsonProperty("PHONE")
    private String phone;
    @JsonProperty("EMAIL")
    private String email;
    private String tkhoanKh;
    @JsonProperty("DCHI")
    private String dchi;
    private String dchiHt;
    private String hdKhac;
    private String tenHdNn;
    private String dtHdNn;
    private String ctyHdNn;
    private String dchiHdNn;
    private String emailHdNn;
    @JsonProperty("HT_HD_KENH")
    private String htHdKenh;
    private String loaiVay;
    @JsonProperty("SO_HD_KENH")
    private String soHdKenh;
    private int hanVay;
    private int hanVayNgay;
    private int ngayGn;
    private String hanVayHl;
    private String hanVayKt;
    private int tienVay;
    private int dnoHt;
    @JsonProperty("MA_DAO")
    private String maDao;
    private String tenDao;
    private String emailNhang;
    @JsonProperty("SO_CIF_KH")
    private String soCifKh;
    private String htTra;
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
    private String htTtoan;
    private int ngayCap;
    private String ckbBamsinh;
    private String cbkDieutri;
    private String ckbDauhieu;
    private String ngayTraNo;
    private int soId;
    private String soIdDt;
    private String nv;
}
