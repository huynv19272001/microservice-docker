package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractDTO {
    private String id;
    private String refId;
    private String selector;
    private String lookup;
    private String attrs;
    private String payload;
    private EcontractBodyDTO body;
}
