package com.lpb.esb.service.cache.model.redis;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class RedisConnect {
    @Value("${spring.redis.ip}")
    String hostName;

    @Value("${spring.redis.portNew}")
    String port;

    @Value("${spring.redis.maxtotal}")
    String maxTotal;

    @Value("${spring.redis.maxidle}")
    String maxIdle;

    @Value("${spring.redis.minidle}")
    String minIdle;
}
