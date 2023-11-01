package com.lpb.esb.etax.inquiry;

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
public class EtaxInfoInquiryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtaxInfoInquiryServiceApplication.class, args);
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
