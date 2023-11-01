package com.lpb.service.bidv.common;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "constant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Constant {
    private String KEY_FACTORY_ALGORITHM;
    private String SIGNATURE_ALGORITHM;
    private String HAS_ROLE;
    private String SERVICE_ID;
    private String PRODUCT_CODE_IMPORT_MESSAGE_CT001;
    private String PRODUCT_CODE_EXPORT_MESSAGE_CT002;
    private String PRODUCT_CODE_RESULTS_MESSAGE_CT003;
    private String PRODUCT_CODE_UPDATE_EXPORTED_MESSAGE_CT005;
    private String PRODUCT_CODE_SEQUENCE_EXPORT_MESSAGE_CT006;
    private String CHECK_VERIFY;
}
