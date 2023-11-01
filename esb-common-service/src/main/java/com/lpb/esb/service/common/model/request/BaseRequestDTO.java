package com.lpb.esb.service.common.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BaseRequestDTO {
    private HeaderInfoDTO header;
    private BodyInfoDTO body;
}

