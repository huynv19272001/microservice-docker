package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class OtpId implements Serializable {
    private String userId;
}
