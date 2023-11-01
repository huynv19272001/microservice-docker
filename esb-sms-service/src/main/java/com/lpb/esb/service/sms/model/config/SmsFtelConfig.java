package com.lpb.esb.service.sms.model.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tudv1 on 2022-05-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class SmsFtelConfig {
    @Value("${fpt.path.login}")
    String pathFptLogin;
    @Value("${fpt.path.push-branchname}")
    String pathFptPushBranchName;
    @Value("${fpt.path.check-is-viettel}")
    String pathCheckIsViettel;
    @Value("${fpt.path.initvec}")
    String initVec;
    @Value("${fpt.path.pricp}")
    String priCp;
    @Value("${fpt.path.pubcp}")
    String pubCp;
    @Value("${fpt.path.pricpvv}")
    String priCpvv;
    @Value("${fpt.path.pubcpvv}")
    String pubCpvv;
}
