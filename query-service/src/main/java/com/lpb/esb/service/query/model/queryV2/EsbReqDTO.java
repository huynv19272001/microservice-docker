package com.lpb.esb.service.query.model.queryV2;

import lombok.*;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbReqDTO<T> {
    private EsbHeaderDTO header;
    private T body;
}
