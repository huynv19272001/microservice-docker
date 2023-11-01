package com.lpb.esb.service.query.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api")
@NoArgsConstructor
@AllArgsConstructor
public class APIConfig {
    private String lePhiTruocBa;
}
