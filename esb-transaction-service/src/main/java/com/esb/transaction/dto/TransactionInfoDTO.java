package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInfoDTO {
    private String trnBrn;
    private String customerNo;
    private String userId;
    private String trnDesc;
    private String transactionId;
    private String valueDt;
    private String confirmTrn;
    private String coreRefNo;
}
