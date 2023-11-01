package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractFieldBoxDTO {
    private double x;
    private double y;
    private double w;
    private double h;
}
