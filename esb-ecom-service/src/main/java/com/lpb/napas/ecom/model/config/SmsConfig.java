package com.lpb.napas.ecom.model.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class SmsConfig {
    private String headerUserId;
    private String headerService;
    private String headerOperation;
    private String headerPassword;
    private String bodyServiceId;
    private String bodySendInfoChannel;
    private String bodySendInfoCategory;
    private String bodySendInfoBranchCode;
    private String apiUrl;
}
