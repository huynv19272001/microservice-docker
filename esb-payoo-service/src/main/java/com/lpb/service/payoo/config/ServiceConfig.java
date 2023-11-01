package com.lpb.service.payoo.config;

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
    @Value(value = "${service.baseUrl}")
    private String baseUrl;

    @Value(value = "${service.partnerCode}")
    private String partnerCode;

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

    @Value(value = "${service.LPBKey}")
    private String LPBKey;

    @Value(value = "${service.cert.prefix}")
    private String certPrefix;

    @Value(value = "${service.cert.pass}")
    private String certPass;

    @Value(value = "${service.cert.pfx}")
    private String certPfx;
}
