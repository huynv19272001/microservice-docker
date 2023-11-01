package com.lpb.esb.service.pti.model.pti.creditbaoan.tinhduno;

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
    private String soThangBh;
    private String soNgayBh;
    private String tienBh;
    private String ngayHl;
    private String ngayGn;
    private String ngayTraNo;
    private String hanVay;
    private String hanVayHl;
    private String dnoHt;
}
