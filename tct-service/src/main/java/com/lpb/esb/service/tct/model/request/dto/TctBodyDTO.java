package com.lpb.esb.service.tct.model.request.dto;

import lombok.*;

import java.util.Map;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TctBodyDTO {
    String soToKhai;
    String maSoThue;
    String loaiThue;

    String maHs;
    String so;

    String maCqThu;

    Map<String, Object> row;

    String maNhtm;
    String soChungtu;
    String soCMND;
    String maSoThueNNT;
    String ngayChungtuTungay;
    String ngayChungtuDenngay;
    String trangThai;
}
