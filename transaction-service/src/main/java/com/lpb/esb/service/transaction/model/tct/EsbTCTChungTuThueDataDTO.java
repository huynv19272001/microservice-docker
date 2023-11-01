package com.lpb.esb.service.transaction.model.tct;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbTCTChungTuThueDataDTO {
    private String so_khung;
    private String ngay_ntien;
    private String cq_qly_thu;
    private String dia_chi_ts;
    private String dac_diem_ptien;
    private String so_may;
    private String ma_nhtm;
    private String huyen;
    private String ngay_chungtu;
    private String diachi_nnthay;
    private String mst_nnthay;
    private String loai_tien;
    private String diengiai_hthuc_nop;
    private String mst;
    private String so_quyet_dinh;
    private String cquan_thamquyen;
    private String diachi_nnt;
    private String ten_nnt;
    private List<ESBTCTChungTuChiTietDTO> chungtu_chitiet;
    private String so_chungtu;
    private String tinh;
    private String so_tk_nhkbnn;
    private String ten_nnthay;
    private String ma_hthuc_nop;
}
