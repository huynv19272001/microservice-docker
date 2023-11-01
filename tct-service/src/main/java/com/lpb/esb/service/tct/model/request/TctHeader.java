package com.lpb.esb.service.tct.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2022-02-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TctHeader {
    String version;
    String senderCode;
    String senderName;
    String receiverCode;
    String receiverName;
    String tranCode;
    String msgId;
    String msgRefId;
    String idLink;
    String sendDate;
    String originalCode;
    String originalName;
    String originalDate;

}
