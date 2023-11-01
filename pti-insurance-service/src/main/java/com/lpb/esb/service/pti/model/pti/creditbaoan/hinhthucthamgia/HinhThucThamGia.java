package com.lpb.esb.service.pti.model.pti.creditbaoan.hinhthucthamgia;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HinhThucThamGia {
    private Data data;
    private String encrypt;
}
