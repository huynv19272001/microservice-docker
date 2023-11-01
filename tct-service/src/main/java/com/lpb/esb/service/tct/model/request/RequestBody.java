package com.lpb.esb.service.tct.model.request;

import lombok.*;

import java.util.Map;

/**
 * Created by tudv1 on 2022-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestBody {
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
