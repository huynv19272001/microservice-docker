package com.lpb.service.vimo;

import com.lpb.service.vimo.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VIMOMain {

    public static void main(String[] args) {
        SpringApplication.run(VIMOMain.class, args);
    }
}
