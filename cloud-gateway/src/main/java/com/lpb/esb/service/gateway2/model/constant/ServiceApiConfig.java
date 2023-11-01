package com.lpb.esb.service.gateway2.model.constant;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tudv1 on 2021-08-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class ServiceApiConfig {
    @Value("${service.api.sequence}")
    private String apiSequenceNext;
}
