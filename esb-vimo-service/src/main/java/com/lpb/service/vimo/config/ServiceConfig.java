package com.lpb.service.vimo.config;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class ServiceConfig {

//    @Value(value = "${service.baseUrl}")
//    private String baseUrl;

    @Value(value = "${service.mcCode}")
    private String mcCode;

    @Value(value = "${service.merchantCode}")
    private String merchantCode;

    @Value(value = "${service.mcAuthUser}")
    private String mcAuthUser;

    @Value(value = "${service.mcAuthPass}")
    private String mcAuthPass;

    @Value(value = "${service.mcChecksumKey}")
    private String mcChecksumKey;

    @Value(value = "${service.mcEncryptKey}")
    private String mcEncryptKey;

    @Value(value = "${service.isSecuredProtocol}")
    private String isSecuredProtocol;

    @Value(value = "${service.connectionRequestTimeout}")
    private String connectionRequestTimeout;

    @Value(value = "${service.connectTimeout}")
    private int connectTimeout;

    @Value(value = "${service.socketTimeout}")
    private int socketTimeout;

    @Value(value = "${service.socketTimeout_checkStatus}")
    private String socketTimeoutCheckStatus;

}
