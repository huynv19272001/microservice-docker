package com.lpb.esb.service.gateway2;

import com.lpb.esb.service.gateway2.config.security.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2021-08-30
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CloudGatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayMain.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
