package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class LpbEofficeDTO {
    private int id;
    private String fileName;
    private String bytes;
}
