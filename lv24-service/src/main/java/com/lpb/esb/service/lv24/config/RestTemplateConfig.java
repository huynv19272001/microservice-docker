package com.lpb.esb.service.lv24.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplateLB() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(2 * 60 * 1000))
            .setConnectTimeout(Duration.ofMillis(45 * 1000))
            .build();

        return restTemplate;
    }
}
