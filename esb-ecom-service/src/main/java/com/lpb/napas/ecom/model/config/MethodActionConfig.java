package com.lpb.napas.ecom.model.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "methodaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class MethodActionConfig {
    private String verifyPayment;
    private String verifyOTP;
    private String purchase;
}
