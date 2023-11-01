package com.lpb.esb.service.tct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2022-02-24
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class TctMain {
    public static void main(String[] args) {
        loadConfig();
        SpringApplication.run(TctMain.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplateLB() {
        return new RestTemplate();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static void loadConfig() {
        if (System.getProperty("javax.net.ssl.trustStore") == null) {
            System.setProperty("javax.net.ssl.trustStore", "tct-service/tct-cert/TCTThue/esb-gdt-gov.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        }
    }
}

