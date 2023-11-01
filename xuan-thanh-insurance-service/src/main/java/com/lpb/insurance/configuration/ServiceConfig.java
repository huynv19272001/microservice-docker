package com.lpb.insurance.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ServiceConfig {
    private String sequence;
    private String serviceId;
    private String hasRole;
    private String token;
    private String expiresToken;
}

