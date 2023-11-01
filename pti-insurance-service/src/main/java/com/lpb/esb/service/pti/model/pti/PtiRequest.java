package com.lpb.esb.service.pti.model.pti;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PtiRequest {
    private String data;
    private String dsDk;
    private String dsTra;
    private String cot;
    private String encrypt;
}
