package com.lpb.esb.service.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tudv1 on 2021-07-23
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class KafkaMain {
    public static void main(String[] args) {
        SpringApplication.run(KafkaMain.class, args);
    }

}
