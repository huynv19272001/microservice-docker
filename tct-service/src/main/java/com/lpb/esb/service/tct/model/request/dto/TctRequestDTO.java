package com.lpb.esb.service.tct.model.request.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TctRequestDTO {
    private TctHeaderDTO tctHeader;
    private TctBodyDTO tctBody;
}
