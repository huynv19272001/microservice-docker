package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class DanhSachHoSoDTO {
    private String tu;
    private String den;
    private String tinhTrang;
    private String tuNgay;
    private String denNgay;
    private String tinhTrangThanhToan;
    private String loaiHopDong;
}
