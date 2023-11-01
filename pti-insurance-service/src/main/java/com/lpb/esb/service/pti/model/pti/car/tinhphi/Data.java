package com.lpb.esb.service.pti.model.pti.car.tinhphi;


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
    @JsonProperty("HANG_XE")
    private String hangXe;
    @JsonProperty("hang_xe")
    private String hangXe1;
    @JsonProperty("hieu_xe")
    private String hieuXe1;
    @JsonProperty("HIEU_XE")
    private String hieuXe;
    private String namSx;
    @JsonProperty("MD_SD")
    private String mdSd;
    @JsonProperty("LOAI_XE")
    private String loaiXe;
    private int soCn;
    private int ttai;
    private int soNbh;
    @JsonProperty("GIO_HL")
    private String gioHl;
    @JsonProperty("NGAY_HL")
    private String ngayHl;
    @JsonProperty("GIO_KT")
    private String gioKt;
    @JsonProperty("NGAY_KT")
    private String ngayKt;
    @JsonProperty("BN")
    private String bn;
    private String gioHlTnds;
    private String ngayHlTnds;
    private String ngayKtTnds;
    private int phiTnds;
    private int ptTangTnds;
    private String tn;
    private int tienTn;
    private int ptTn;
    private int phiTn;
    private int tienTt;
    private int ptTt;
    private int phiTt;
    private int tienTk;
    private int ptTk;
    private int phiTk;
    @JsonProperty("TL")
    private String tl;
    private String goiTl;
    private int tienTl;
    private int ptTl;
    private int phiTl;
    private String goiTlp;
    private int tienTlp;
    private int ptTlp;
    private int phiTlp;
    private String goiTlk;
    private int tienTlk;
    private double ptTlk;
    private int phiTlk;
    private String tienTnv;
    private int phiTnv;
    private int tienTh;
    private int ptTh;
    private int phiTh;
    @JsonProperty("TV")
    private String tv;
    private int giaXeTk;
    private int giaXe;
    private int ptSang;
    private double ptTv;
    private int phiTv;
    private double ptCb;
    private double ptG;
    private double ptGia;
    private int phiTvG;
    private int mucKtru;
    private String giaXeNd;
    private int phiTvmr;
    private String maKm;
    private int ptGiam;
    private int giam;
    private String htTtoan;
    private String htTra;
    @JsonProperty("DVI_SL")
    private String dviSl;
    private String kieuHd;
    private String soHdG;
    @JsonProperty("NGAY_HT")
    private int ngayHt;
    private String ttrang;
    private String inctrMa;
    private String inctrKieu;
    private int phi;
    private int thue;
    private int ttoan;
    @JsonProperty("NGAY_CAP")
    private int ngayCap;
    private String soHd;
    private String htHd;
    private String soId;
    private String soIdDt;
    private String dkmr;
    private String dkLoai;
    private String loai;
    private String maten;
    private String ntPhi;
}
