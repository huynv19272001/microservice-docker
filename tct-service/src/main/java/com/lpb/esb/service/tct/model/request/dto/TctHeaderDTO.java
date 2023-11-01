package com.lpb.esb.service.tct.model.request.dto;

import lombok.*;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TctHeaderDTO {
    String version;
    String senderCode;
    String senderName;
    String receiverCode;
    String receiverName;
    String tranCode;
    String msgId;
    String msgRefid;
    String idLink;
    String sendDate;
    String originalCode;
    String originalName;
    String originalDate;
    String errorCode;
    String errorDesc;
    String spare1;
    String spare2;
    String spare3;
}
