package com.lpb.esb.service.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by tudv1 on 2021-09-09
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableScheduling
public class JobMain {
    public static void main(String[] args) {
        SpringApplication.run(JobMain.class, args);
    }
}
