package com.lpb.esb.service.file.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Getter
@Setter
public class AutoDebitDaiichiConfig {
    @Value("${autodebit-daiichi.serviceId}")
    private String serviceId;
    @Value("${autodebit-daiichi.service}")
    private String service;
    @Value("${autodebit-daiichi.operation}")
    private String operation;
    @Value("${autodebit-daiichi.productCodeDangKy}")
    private String productCodeDangKy;
    @Value("${autodebit-daiichi.productCodeGiaoDich}")
    private String productCodeGiaoDich;
}
