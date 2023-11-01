package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestDTO {
    private EsbDTO header;
    private EcontractDTO body;
}
