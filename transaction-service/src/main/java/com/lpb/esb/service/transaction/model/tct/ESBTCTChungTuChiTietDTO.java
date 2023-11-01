package com.lpb.esb.service.transaction.model.tct;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ESBTCTChungTuChiTietDTO {
    public String tieumuc;
    public String ky_thue;
    public String thongtin_khoannop;
    public String so_tien;
    public String chuong;
}
