package com.lpb.esb.service.tct.model.config;

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
public class TctFileConfig {
    @Value("${file.tct.prefix}")
    String filePrefix;
    @Value("${file.tct.xml.request}")
    String tctXmlBodyRequest;
    @Value("${file.tct.xml.header}")
    String tctXmlHeader;
    @Value("${file.tct.xml.lptb}")
    String tctXmlBodyLptb;
    @Value("${file.tct.xml.tcn}")
    String tctXmlBodyTcn;
    @Value("${file.tct.xml.tnd}")
    String tctXmlBodyTnd;
    @Value("${file.tct.xml.nop-chung-tu}")
    String tctXmlBodyNopChungTu;
    @Value("${file.tct.xml.huy-chung-tu}")
    String tctXmlBodyHuyChungTu;
    @Value("${file.tct.xml.cmnd}")
    String tctXmlBodyCmnd;
    @Value("${file.tct.xml.mst}")
    String tctXmlBodyMst;
    @Value("${file.tct.xml.thu-nop-mst}")
    String tctXmlBodyThuNopMst;
    @Value("${file.tct.xml.truy-van-chung-tu}")
    String tctXmlBodyTruyVanChungTu;


    @Value("${file.tct.cert.prefix}")
    String certPrefix;
    @Value("${file.tct.cert.pfx}")
    String certPfx;
    @Value("${file.tct.cert.cer}")
    String certCer;

    @Value("${file.tct.gov.jks}")
    String govJks;
    @Value("${file.tct.gov.pass}")
    String govPass;
}
