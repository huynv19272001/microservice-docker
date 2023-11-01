package com.lpb.service.mafc.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration

public class ServiceConfig {

    @Value(value = "${service.authorization}")
    private String authorization;

    @Value(value = "${service.connectTimeout}")
    private int connectTimeout;

    @Value(value = "${service.socketTimeout}")
    private int socketTimeout;

    @Value(value = "${service.channel}")
    private String channel;

    @Value(value = "${service.collector}")
    private String collector;

}
