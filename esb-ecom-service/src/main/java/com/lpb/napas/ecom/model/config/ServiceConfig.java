package com.lpb.napas.ecom.model.config;

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
    private String requestTxn;
    private String userNapas;
    private String serviceIdAtm;
    private String channel;
    private String contendSms;
    private String headerSource;
    private String headerPassword;
    private String headerUbscomp;
    private String headerUserId;
    private String headerModuleId;
    private String headerService;
    private String headerOperation;
    private String headerAction;
    private String headerFunctionId;
    private String bodySourceCode;
    private String bodyTxnCode;
    private String esbMakerIdUpload;
    private String esbCheckerIdUpload;
    private String secretKey;
}
