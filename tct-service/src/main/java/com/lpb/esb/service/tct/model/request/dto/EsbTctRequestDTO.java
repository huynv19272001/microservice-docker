package com.lpb.esb.service.tct.model.request.dto;

import lombok.*;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbTctRequestDTO<T> {
    private EsbTctHeaderDTO header;
    private T body;
}
