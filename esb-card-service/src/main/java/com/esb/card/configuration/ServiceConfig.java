package com.esb.card.configuration;

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
    private String headerSource;
    private String headerPassword;
    private String headerUbsComp;
    private String headerUserId;
    private String headerModuleId;
    private String headerService;
    private String headerOperationGetAccountList;
    private String headerOperationGetAvlBalance;
    private String headerAction;
    private String headerFuntionId;
    private String bodySourceCode;
    private String bodyTxnCode;
    private String esbMakerIdUpload;
    private String esbCheckerIdUpload;
    private String bodyRecordPerPage;
    private String bodyPageNumber;
    private String userCardCore;
}
