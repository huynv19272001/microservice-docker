package com.lpb.service.mafc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MAFCMain {
    public static void main(String[] args) {
        SpringApplication.run(MAFCMain.class, args);
    }

}
