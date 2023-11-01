package com.lpb.esb.service.lv24.model.corehr;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbResponse<T> {
    private T core;
    private T hr;
}
