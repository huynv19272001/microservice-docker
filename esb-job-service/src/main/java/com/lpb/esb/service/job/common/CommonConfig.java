package com.lpb.esb.service.job.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Created by tudv1 on 2021-07-12
 */
@Configuration
public class CommonConfig {
    // Create a bean for restTemplate to call services
    @Bean
    @LoadBalanced        // Load balance between service instances running at different ports.
    public RestTemplate restTemplateLoadBalancer() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(5 * 60 * 1000))
            .setConnectTimeout(Duration.ofMillis(5 * 60 * 1000))
            .build();

        return restTemplate;
    }
}
