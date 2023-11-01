package com.lpb.esb.service.lv24.model.corehr;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CoreRequest {
    private String msgId;
    private String cif;
}
