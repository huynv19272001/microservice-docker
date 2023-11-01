package com.lpb.esb.service.common.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class BodyInfoDTO<T> {
    private T data;
}

