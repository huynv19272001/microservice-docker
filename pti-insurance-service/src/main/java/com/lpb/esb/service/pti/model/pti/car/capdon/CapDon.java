package com.lpb.esb.service.pti.model.pti.car.capdon;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lpb.esb.service.pti.model.pti.DsTra;
import com.lpb.esb.service.pti.model.pti.car.DsDk;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CapDon {
    private Data data;
    private List<DsDk> dsDk;
    private List<DsTra> dsTra;
}
