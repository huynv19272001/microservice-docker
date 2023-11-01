package com.lpb.napas.ecom.dto;

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
