package com.lpb.service.bidv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class BIDVMain {
//    final static String KEYSTORE_PASSWORD = "123456";
//
//    static {
//        //local
////        System.setProperty("javax.net.ssl.trustStore", "bidv-service/key/bidv-call-api.jks");
//        //test and live
//        System.setProperty("javax.net.ssl.trustStore", "key/bidv-call-api.jks");
//        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
//
//        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//            new javax.net.ssl.HostnameVerifier() {
//
//                public boolean verify(String hostname,
//                                      javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals("localhost")) {
//                        return true;
//                    }
//                    return false;
//                }
//            });
//    }
//
//    @Bean
//    public RestTemplate template() throws Exception {
//        RestTemplate template = new RestTemplate();
//        return template;
//    }

    public static void main(String[] args) {
        Environment env = SpringApplication.run(BIDVMain.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name").toUpperCase();
        String port = env.getProperty("server.port");
        System.out.println("-------------------------START " + appName + " Application------------------------------");
        System.out.println("   Application         : " + appName);
        System.out.println("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
    }
}
