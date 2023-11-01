package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBodyInfoDTO<T> {
    private EsbBillInfoDTO billInfo;
    private TransactionInfoDTO transactionInfo;
    private T data;
}
