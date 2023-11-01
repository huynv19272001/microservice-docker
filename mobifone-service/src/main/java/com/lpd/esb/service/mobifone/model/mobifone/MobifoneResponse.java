package com.lpd.esb.service.mobifone.model.mobifone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class MobifoneResponse {
    private String status;
    private String message;
    private String transactionId;
    private String payload;
    private String requestId;
}
