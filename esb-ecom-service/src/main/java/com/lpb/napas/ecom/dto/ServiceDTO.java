package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDTO {
    private String merchantId;
    private String serviceId;
    private String productCode;
    private String receiveAccount;
    private String requestAccount;
}
