package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class MobifoneRequest {
    private String token;
    private ArrayList<MobifoneRequestBody> body;
}
