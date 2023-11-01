package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import java.io.Serializable;

/**
 * Created by tudv1 on 2021-07-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ServiceProductID implements Serializable {
    private String productCode;
    private String serviceId;
}
