package com.lpb.esb.infocustomerbill.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CommonConfig {
    // Create a bean for restTemplate to call services
    @Bean
    @LoadBalanced        // Load balance between service instances running at different ports.
    public RestTemplate restTemplateLoadBalancer() {
        return new RestTemplate();
    }

    @Bean
    //30s
    public RestTemplate restTemplate() {

        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(300 * 1000))
                .setConnectTimeout(Duration.ofMillis(300 * 1000))
                .build();
    }
}
