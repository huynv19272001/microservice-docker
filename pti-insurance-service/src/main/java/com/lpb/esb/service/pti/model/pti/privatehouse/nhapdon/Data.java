package com.lpb.esb.service.pti.model.pti.privatehouse.nhapdon;

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
    private String nhomDn;
    private String tenDn;
    private String soDkkd;
    private String dchiDn;
    private String soCifKh;
    private String maThue;
    private String ddDn;
    private String cvuDn;
    private String ten;
    private String phone;
    private String email;
    private String soHdKenh;
    private String ngayGn;
    private String hanVay;
    private String maDao;
    private String tenDao;
    private String emailNhang;
    private String loaiTh;
    private String nguoiTh;
    private String dchiNguoiTh;
    private String hdKhac;
    private String tenHdNn;
    private String ctyHdNn;
    private String dtHdNn;
    private String dchiHdNn;
    private String emailHdNn;
    @JsonProperty("NV")
    private String nv;
    private String kvuc;
    private String tinh;
    private String huyen;
    private int gtriTs;
    private String ddiemBh;
    private String namSx;
    private String mdSd;
    private String lhNv;
    private String dtBh;
    private String dtBhDes;
    private String lhNvDes;
    @JsonProperty("n_nghe_mota")
    private String nNgheMota;
    private String quyTac;
    private String mucKtru;
    @JsonProperty("GIO_HL")
    private String gioHl;
    @JsonProperty("NGAY_HL")
    private String ngayHl;
    @JsonProperty("GIO_KT")
    private String gioKt;
    @JsonProperty("NGAY_KT")
    private String ngayKt;
    private int tienBh;
    private double pt;
    private String tsbt;
    private String tncc;
    private int soThangBh;
    private int soNgayBh;
    private String htTtoan;
    private String htTra;
    private String ttoan;
    private String phi;
    private String thue;
    private String quyTacDes;
    private String ntPhi;
    private String dviSl;
    private String ttrang;
    private String soHd;
    private String soId;
    private String ngayCap;
    private int ngayHt;
    private String kieuHd;
    private String soHdG;
    private String tienTs;
    private String tienTncc;
}
