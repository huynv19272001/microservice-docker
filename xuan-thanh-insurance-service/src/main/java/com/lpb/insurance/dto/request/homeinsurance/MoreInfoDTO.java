package com.lpb.insurance.dto.request.homeinsurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoreInfoDTO {
    @JsonProperty("MaDonVi")
    private String maDonVi;
    @JsonProperty("SoId")
    private String soId;
    @JsonProperty("Ten")
    private String ten;
    @JsonProperty("TenTA")
    private String tenTA;
    @JsonProperty("TinhTrang")
    private String tinhTrang;
    @JsonProperty("SoTT")
    private int soTT;
}
