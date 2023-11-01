package com.lpb.esb.service.transaction.model;

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
    private EsbTransactionDTO transactionInfo;
}
