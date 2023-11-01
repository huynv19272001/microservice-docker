package com.lpb.esb.service.transaction.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBillInfoDTO {
    private String billCode;
    private String billDesc;
    private String amtUnit;
    private String settledAmount;
    private String otherInfo;
    private String billType;
    private String billStatus;
    private String billId;
    private String billAmount;
    private String paymentMethod;
}
