package com.lpb.esb.service.lv24.model.config;

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
public class Lv24FileConfig {
    @Value("${file.lv24.prefix}")
    String filePrefix;
    @Value("${file.lv24.xml.bdsd-request-data}")
    String lv24BdsdNotiRequestData;
    @Value("${file.lv24.xml.bdsd-execute}")
    String lv24BdsdExecute;
}
