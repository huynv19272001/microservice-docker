package com.lpb.esb.service.common.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2022-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class CacheEntity {
    private String module;
    private String key;
    private String value;
    private Long ttl = 3600l;
    private String hash;
    private int db = 0;
}
