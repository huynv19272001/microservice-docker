package com.lpb.esb.service.pti.model.pti.privatehouse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LayTyLePhiMacDinhVaNamSuDung {
    @NotBlank
    private String ngayHt;
    @NotBlank
    private String lhNv;
    @NotBlank
    private String dtBh;
    @NotBlank
    private String loai;
}
