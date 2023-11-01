package com.lpb.napas.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitValidAmtRequest {
    private Integer amt;
    private Integer minAmt;
    private Integer maxAmt;
    private String serviceId;
    private String customerNo;
}
