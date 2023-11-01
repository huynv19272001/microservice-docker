package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractHeaderFieldsDTO {
    private String id;
    private String name;
    private String type;
    private String value;
}
