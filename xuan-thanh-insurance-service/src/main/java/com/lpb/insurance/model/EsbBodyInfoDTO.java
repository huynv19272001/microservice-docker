package com.lpb.insurance.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBodyInfoDTO<T> {
    private T data;
}
