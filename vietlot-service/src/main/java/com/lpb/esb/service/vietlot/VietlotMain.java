package com.lpb.esb.service.vietlot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tudv1 on 2021-10-05
 */
@SpringBootApplication
@EnableEurekaClient
public class VietlotMain {
    public static void main(String[] args) {
        SpringApplication.run(VietlotMain.class, args);
    }
}
