/**
 * @author Trung.Nguyen
 * @date 26-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.config;

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
public class ServiceApiConfig {

	@Value("${service.flex.findcust}")
    private String flexQueryCustUrl;

	@Value("${service.flex.findacc}")
    private String flexQueryAccUrl;

    @Value("${service.flex.findaccdtl}")
    private String flexQueryAccDtlUrl;

	@Value("${service.card.finddrcard}")
    private String findDebitCardUrl;

    @Value("${service.card.findlstcard}")
    private String findListCardUrl;

	@Value("${service.otp.create}")
    private String otpCreateUrl;

	@Value("${service.otp.verify}")
    private String otpVerifyUrl;

    @Value("${service.smsgw.category}")
    private String smsgwCategoryUrl;

}
