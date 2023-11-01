package com.lpb.esb.service.lv24.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBodyInfoDTO<T> {
    private T data;
}
