package com.lpb.esb.service.transaction.model.tct;

import lombok.*;

/**
 * Created by tudv1 on 2022-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbHeader {
    private String serviceId;
    private String productCode;
    private String hasRole;
}
