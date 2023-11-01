package com.lpb.esb.service.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tudv1 on 2021-07-08
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CustomerMain {
    public static void main(String[] args) {
        SpringApplication.run(CustomerMain.class, args);
    }
}
