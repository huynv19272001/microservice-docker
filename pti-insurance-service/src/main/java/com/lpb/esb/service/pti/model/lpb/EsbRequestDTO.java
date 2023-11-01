package com.lpb.esb.service.pti.model.lpb;

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
