package com.lpb.napas.ecom.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTransactionDTO {
    private String appMsgId;
    private Date valueDt;
    private String coreRefNo;
    private String messageId;
    private String errorCode;
    private String errorDesc;
}
