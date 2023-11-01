package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class LpbRequestDTO {
    private EsbDTO header;
    private LpbEofficeDTO body;
}
