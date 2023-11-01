package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbPartnerAccountDetailId implements Serializable {
    private String providerId;
    private String serviceId;
    private String productCode;
    private String accountType;
}
