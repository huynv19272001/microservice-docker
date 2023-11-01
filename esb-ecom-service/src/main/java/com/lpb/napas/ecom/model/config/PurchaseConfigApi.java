package com.lpb.napas.ecom.model.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "purchaseconfigapi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class PurchaseConfigApi {
    private String sourceCode;
    private String txnCode;
    private String makerId;
    private String checkerId;
    private String countPurchase;
}
