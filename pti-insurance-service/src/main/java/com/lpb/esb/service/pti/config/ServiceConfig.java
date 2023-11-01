package com.lpb.esb.service.pti.config;

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
    private String serviceId;
    private String hasRole;
    private String headerProductCode;
}

