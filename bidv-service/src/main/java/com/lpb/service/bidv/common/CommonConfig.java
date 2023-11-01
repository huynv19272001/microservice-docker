package com.lpb.service.bidv.common;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.time.Duration;

/**
 * Created by tudv1 on 2021-07-12
 */
@Configuration
public class CommonConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(60 * 1000))
            .setConnectTimeout(Duration.ofMillis(15 * 1000))
            .build();

        return restTemplate;
    }

    @Bean
    public RestTemplate getRestTemplatePartner() throws Exception {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(csf)
            .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(Duration.ofMillis(2 * 60 * 1000))
            .setConnectTimeout(Duration.ofMillis(45 * 1000))
            .build();
        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }

//    @Bean
//    @LoadBalanced        // Load balance between service instances running at different ports.
//    public RestTemplate restTemplateLoadBalancer() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplateBuilder()
//            .setReadTimeout(Duration.ofMillis(5 * 60 * 1000))
//            .setConnectTimeout(Duration.ofMillis(5 * 60 * 1000))
//            .build();
//
//        return restTemplate;
//    }

}
