package com.lpb.esb.service.sms.model.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tudv1 on 2022-02-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class SmsFileConfig {
    @Value("${file.sms.prefix}")
    String filePrefix;
    @Value("${file.sms.xml.esb}")
    String esbXmlBodyRequest;
    @Value("${file.sms.xml.vnp}")
    String vnpXmlBodyRequest;
}
