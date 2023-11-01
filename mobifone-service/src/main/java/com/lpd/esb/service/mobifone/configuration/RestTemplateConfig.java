package com.lpd.esb.service.mobifone.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    // Create a bean for restTemplate to call services
    @Bean
    @LoadBalanced        // Load balance between service instances running at different ports.
    public RestTemplate getRestTemplateLB() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(2 * 60 * 1000))
            .setConnectTimeout(Duration.ofMillis(45 * 1000))
            .build();

        return restTemplate;
    }

}
