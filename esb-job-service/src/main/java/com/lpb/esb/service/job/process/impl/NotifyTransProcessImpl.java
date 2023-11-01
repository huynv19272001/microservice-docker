package com.lpb.esb.service.job.process.impl;

import com.lpb.esb.service.common.utils.DateUtils;
import com.lpb.esb.service.job.process.NotifyTransProcess;
import com.lpb.esb.service.job.utils.PkgEsbUtilConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2021-09-14
 */
@Component
@Log4j2
public class NotifyTransProcessImpl implements NotifyTransProcess {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void notifyTransaction(String accNo, String jobId, String urlWs, String serviceId, String productCode) {
        try {
//            String content = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:trans-scan-example.txt").toPath()));
            String content = buildMS();
            String msgId = "JOB" + DateUtils.convertTimeLong(System.currentTimeMillis(), "ddMMyyyyHHmmSS");
            String soapBody = String.format(content
                , msgId
                , PkgEsbUtilConstant.USER_ESB
                , PkgEsbUtilConstant.SERVICE
                , PkgEsbUtilConstant.OPERATION
                , PkgEsbUtilConstant.PASS_ESB
                , serviceId
                , productCode
                , jobId
                , accNo
            );

            log.info(soapBody);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            try {
                HttpEntity<String> request = new HttpEntity<String>(soapBody, httpHeaders);

                ResponseEntity<String> response = restTemplate.postForEntity(urlWs, request, String.class);
                log.info("Response code: {} body {}", response.getStatusCodeValue(), response.getBody());
            } catch (Exception e) {
                log.error("error when execute request: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("error when send to notify service: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private String buildMS() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<soapenv:Envelope\n");
        sb.append("	xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n");
        sb.append("	xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSExternalService\">\n");
        sb.append("	<soapenv:Header/>\n");
        sb.append("	<soapenv:Body>\n");
        sb.append("		<fcub:EXTERNALSERVICE_REQ>\n");
        sb.append("			<FCUBS_HEADER>\n");
        sb.append("				<MSGID>%s</MSGID>\n");
        sb.append("				<USERID>%s</USERID>\n");
        sb.append("				<SERVICE>%s</SERVICE>\n");
        sb.append("				<OPERATION>%s</OPERATION>\n");
        sb.append("				<PASSWORD>%s</PASSWORD>\n");
        sb.append("			</FCUBS_HEADER>\n");
        sb.append("			<FCUBS_BODY>\n");
        sb.append("				<SERVICE_IO>\n");
        sb.append("					<SERVICE_ID>%s</SERVICE_ID>\n");
        sb.append("					<PRODUCT_CODE>%s</PRODUCT_CODE>\n");
        sb.append("				</SERVICE_IO>\n");
        sb.append("				<JOB_INFO>\n");
        sb.append("					<JOB_ID>%s</JOB_ID>\n");
        sb.append("					<JOB_AC_NO>%s</JOB_AC_NO>\n");
        sb.append("				</JOB_INFO>\n");
        sb.append("			</FCUBS_BODY>\n");
        sb.append("		</fcub:EXTERNALSERVICE_REQ>\n");
        sb.append("	</soapenv:Body>\n");
        sb.append("</soapenv:Envelope>\n");

        return sb.toString();
    }
}
