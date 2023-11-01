//package com.lpb.esb.service.gateway2.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by tudv1 on 2021-08-30
// */
//@Configuration
//public class SpringCloudConfig {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder
//            .routes()
//            .route(
//                r -> r
//                    .path("/esb-auth-service/**")
//                    .uri("lb://ESB-AUTH-SERVICE")
//                    .id("esb-auth-service")
//            )
//            .route(
//                r -> r.path("/esb-sms-service/**")
//                    .filters(f -> f.rewritePath("/esb-sms-service/(?<remaining>.*)", "/${remaining}"))
//                    .uri("lb://ESB-SMS-SERVICE")
//                    .id("esb-sms-service")
//            )
//            .route(
//                r -> r.path("/esb-notification-service/**")
//                    .filters(f -> f.rewritePath("/esb-notification-service/(?<remaining>.*)", "/${remaining}"))
//                    .uri("http://10.38.21.34:18901")
//                    .id("esb-notification-service")
//            )
//            .build();
//    }
//
//}
