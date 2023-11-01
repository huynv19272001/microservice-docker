package com.lpb.esb.service.query.model.tct;

import lombok.*;

/**
 * Created by tudv1 on 2022-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class RequestData {
    private EsbHeader esbHeader;
    private TctHeader tctHeader;
    private RequestBody body;
}
