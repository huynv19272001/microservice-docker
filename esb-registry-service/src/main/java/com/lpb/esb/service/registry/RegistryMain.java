package com.lpb.esb.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by tudv1 on 2021-07-08
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryMain {
    public static void main(String[] args) {
        SpringApplication.run(RegistryMain.class, args);
    }
}
