package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import java.io.Serializable;

/**
 * Created by tudv1 on 2021-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbPermissionID implements Serializable {
    private String hasRole;
    private String serviceId;
}
