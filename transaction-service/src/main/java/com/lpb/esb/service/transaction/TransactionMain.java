package com.lpb.esb.service.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2022-03-01
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class TransactionMain {
    public static void main(String[] args) {
        Environment env = SpringApplication.run(TransactionMain.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name").toUpperCase();
        String port = env.getProperty("server.port");
        System.out.println("-------------------------START " + appName + " Application------------------------------");
        System.out.println("   Application         : " + appName);
        System.out.println("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplateLB() {
        return new RestTemplate();
    }
}

