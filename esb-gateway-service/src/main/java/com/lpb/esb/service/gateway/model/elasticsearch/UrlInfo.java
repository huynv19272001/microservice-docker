package com.lpb.esb.service.gateway.model.elasticsearch;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * Created by tudv1 on 2021-08-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class UrlInfo {
    private String scheme;
    private String host;
    private int port;
    private String path;
    @Field(name = "full_url")
    private String fullUrl;
}
