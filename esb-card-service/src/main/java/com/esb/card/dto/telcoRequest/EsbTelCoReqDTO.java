package com.esb.card.dto.telcoRequest;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbTelCoReqDTO {
//    private String cardId;
    private String phoneNo;
    private String telCode;
}
