package com.esb.transaction.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class OtpConfig {
    private String length;
}
