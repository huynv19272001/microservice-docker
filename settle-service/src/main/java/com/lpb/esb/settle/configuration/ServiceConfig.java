package com.lpb.esb.settle.configuration;

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
    public String service_info;
}
