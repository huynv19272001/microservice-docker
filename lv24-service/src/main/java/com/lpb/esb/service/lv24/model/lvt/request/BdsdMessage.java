package com.lpb.esb.service.lv24.model.lvt.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BdsdMessage {
    private String transDate;
    private String mobileNo;
    private String accountNo;
    private String smsContent;
    private String transRefno;
}
