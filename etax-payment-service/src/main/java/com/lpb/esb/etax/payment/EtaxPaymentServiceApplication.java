package com.lpb.esb.etax.payment;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class EtaxPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtaxPaymentServiceApplication.class, args);
	}
	
	@Bean
    @LoadBalanced
    RestTemplate restTemplate() {
//        return new RestTemplate();
		final int minTimeout = 120000;
		return new RestTemplateBuilder()
		        .setConnectTimeout(Duration.ofMillis(minTimeout))
		        .setReadTimeout(Duration.ofMillis(minTimeout))
		        .build();
    }

}
