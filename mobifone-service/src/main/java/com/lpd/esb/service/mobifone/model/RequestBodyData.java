package com.lpd.esb.service.mobifone.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestBodyData {
    private String phoneNumber;
    private String custCode;
    private String settlementAmount;
    private String settlementDate;
}
