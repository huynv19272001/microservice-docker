//package com.lpb.esb.service.tct.config;
//
//import com.lpb.esb.service.tct.model.config.TctFileConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import javax.annotation.PostConstruct;
//
///**
// * Created by tudv1 on 2022-03-09
// */
//@Configuration
//public class SSLConfig {
//    @Autowired
//    private Environment env;
//    @Autowired
//    TctFileConfig tctFileConfig;
//
//    @PostConstruct
//    private void configureSSL() {
//        //set to TLSv1.1 or TLSv1.2
////        System.setProperty("https.protocols", "TLSv1.1");
//
//        System.setProperty("javax.net.ssl.trustStore", tctFileConfig.getCertPrefix() + tctFileConfig.getGovJks());
//        System.setProperty("javax.net.ssl.trustStorePassword", tctFileConfig.getCertPrefix() + tctFileConfig.getGovPass());
//    }
//}
