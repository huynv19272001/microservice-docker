/**
 * @author Trung.Nguyen
 * @date 21-Apr-2022
 * */
package com.lpb.esb.flex.query.model.soapclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class SoapURLConfig {

	@Value("${service.flexcubs.inquiry:}")
    private String flexInquiryUri;

    @Value("${service.smscu.inquiry:}")
    private String smscInquiryUri;
}
