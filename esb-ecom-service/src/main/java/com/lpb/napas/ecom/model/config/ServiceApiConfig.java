package com.lpb.napas.ecom.model.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ServiceApiConfig {
    private String listCardInfo;
    private String debitCardInfo;
    private String otpCreateOtp;
    private String otpVerifyOtp;
    private String transactionInitTransaction;
    private String transactionUploadTransferJrn;
    private String transactionGetInitTransaction;
    private String getListAccount;
    private String getAvlBalance;
}
