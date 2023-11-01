package com.lpb.esb.service.fileconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tudv1 on 2022-06-23
 */
@SpringBootApplication
@EnableEurekaClient
public class FileConverterApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileConverterApplication.class, args);
    }
}
