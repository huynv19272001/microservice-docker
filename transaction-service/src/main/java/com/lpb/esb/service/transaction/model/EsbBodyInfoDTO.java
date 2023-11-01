package com.lpb.esb.service.transaction.model;

import com.lpb.esb.service.transaction.model.tct.EsbTCTChungTuThueInfoDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBodyInfoDTO<T> {
    private EsbTransactionDTO transactionInfo;
    private EsbBillInfoDTO billInfo;
    private EsbTCTChungTuThueInfoDTO billThueInfo;
    private T data;
}
