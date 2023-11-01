package com.lpb.esb.service.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tudv1 on 2021-07-14
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CacheMain {
    public static void main(String[] args) {
        SpringApplication.run(CacheMain.class, args);
    }
}
