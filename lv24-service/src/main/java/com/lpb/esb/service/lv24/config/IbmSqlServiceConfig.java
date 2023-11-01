package com.lpb.esb.service.lv24.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class IbmSqlServiceConfig {
    @Value("${constant.ibmSqlService.queryHrLv24V.url}")
    String queryHrLv24VUrl;
    @Value("${constant.ibmSqlService.queryHrLv24V.header.userId}")
    String queryHrLv24VUserId;
    @Value("${constant.ibmSqlService.queryHrLv24V.header.service}")
    String queryHrLv24VService;
    @Value("${constant.ibmSqlService.queryHrLv24V.header.operation}")
    String queryHrLv24VOperation;
    @Value("${constant.ibmSqlService.queryHrLv24V.header.password}")
    String queryHrLv24VPassword;
    @Value("${constant.ibmSqlService.queryHrLv24V.body.serviceId}")
    String queryHrLv24VServiceId;
    @Value("${constant.ibmSqlService.queryHrLv24V.body.productCode}")
    String queryHrLv24VProductCode;
}
