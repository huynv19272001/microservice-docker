package com.lpb.esb.service.pti.model.pti.creditbaoan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChiTiet {
    private String soId;
    private String maDvi;
    private String kieuHd;
    private String htHd;
    private String soHdG;
    private String sao;
}
