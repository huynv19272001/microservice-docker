//package com.lpb.esb.service.lv24.config;
//
//import com.lpb.esb.service.lv24.util.LvtClient;
//import com.lpb.esb.service.lv24.util.SqlServiceClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//@Configuration
//public class Lv24Configuration {
//    @Bean
//    public Jaxb2Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // this package must match the package in the <generatePackage> specified in
//        // pom.xml
//        marshaller.setContextPaths("com.lpb.middleware.lv24", "com.lpb.middleware.sqlservice");
//        return marshaller;
//    }
//
//    @Bean
//    public LvtClient viVietConnectClient(Jaxb2Marshaller marshaller) {
//        LvtClient client = new LvtClient();
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//        return client;
//    }
//
//    @Bean
//    public SqlServiceClient sqlServiceClient(Jaxb2Marshaller marshaller) {
//        SqlServiceClient client = new SqlServiceClient();
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//        return client;
//    }
//}
