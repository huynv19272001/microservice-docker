package com.lpb.esb.settle.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(60 * 1000))
            .setConnectTimeout(Duration.ofMillis(15 * 1000))
            .build();

        return restTemplate;
    }

    @Bean
    public RestTemplate getRestTemplatePartner() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(2 * 60 * 1000))
            .setConnectTimeout(Duration.ofMillis(45 * 1000))
            .build();

        return restTemplate;
    }
}

