package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FCubsErrorDTO {
    private String eCode;
    private String eDesc;
    private String refCode;
    private String refDesc;
}
