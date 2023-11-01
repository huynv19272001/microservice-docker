package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbQueryTransInfoDTO {
    private String fromDt;
    private String toDt;
    private EsbServiceDTO serviceInfo;
    private EsbBillInfoDTO transactionInfo;
}
