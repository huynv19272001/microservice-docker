package com.lpb.esb.service.pti.model.pti.creditbaoan.nhapdon;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lpb.esb.service.pti.model.pti.DsTra;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NhapDon {
    private Data data;
    private List<DsTra> dsTra;
}
