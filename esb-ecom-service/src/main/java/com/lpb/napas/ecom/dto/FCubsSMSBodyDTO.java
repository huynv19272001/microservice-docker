package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FCubsSMSBodyDTO {
    private String serviceId;
    private String productCode;
    private String mobileNumber;
    private String content;
    private String channel;
    private String cateGory;
    private String branchCode;
}
