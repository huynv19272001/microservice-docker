package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class MobifoneRequestBody {
    private String fieldId;
    private String value;
}
