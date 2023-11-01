package com.lpb.esb.service.transaction.model.tct;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbTCTChungTuThueInfoDTO {
    private String name;
    private String type;
    private EsbTCTChungTuThueDataDTO chungtu;
}
