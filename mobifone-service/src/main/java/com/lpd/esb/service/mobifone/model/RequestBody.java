package com.lpd.esb.service.mobifone.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestBody {
    private RequestBodyTransactionInfo transactionInfo;
    private RequestBodyData data;
}
