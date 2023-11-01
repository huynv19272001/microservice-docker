package com.lpb.insurance.model;

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
