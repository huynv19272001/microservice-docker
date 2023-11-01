package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by tudv1 on 2022-07-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbPartnerAccountId  implements Serializable {
    private String providerId;
    private String serviceId;
}
