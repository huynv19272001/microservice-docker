package com.lpd.esb.service.econtract.model.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class EofficeXmlConfig {
    @Value("${file.eoffice.prefix}")
    String filePrefix;
    @Value("${file.eoffice.xml.request}")
    String eofficeReq;
}
