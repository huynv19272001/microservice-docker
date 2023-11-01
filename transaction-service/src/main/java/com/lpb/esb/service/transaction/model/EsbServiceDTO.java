package com.lpb.esb.service.transaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbServiceDTO {
    private String serviceId;
    private String productCode;
    private String requestAccount;
    private String receiveAccount;
    private String merchantId;
}
