package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbRequestDTO {
    private EsbHeaderInfoDTO header;
    private EsbBodyInfoDTO body;
}
