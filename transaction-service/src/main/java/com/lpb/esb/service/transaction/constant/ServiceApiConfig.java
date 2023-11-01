package com.lpb.esb.service.transaction.constant;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
