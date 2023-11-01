package com.lpb.esb.service.sms.process.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.utils.VnpUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by tudv1 on 2022-05-16
 */
@Component
@Log4j2
public class SmsSendVNPProcessImpl {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    VnpUtils vnpUtils;
    public ExecuteModel sendVNPSms(ServiceInfo serviceInfo, EsbSmsRequest esbSmsRequest) {
//        String message = RSAUtil.encodeBase64(
//            esbSmsRequest.getEsbBody().getContent()
//                .getBytes(StandardCharsets.UTF_8)
//        ).replaceAll("\n", "").replaceAll("\r", "");

        JSONObject jsonObject = new JSONObject()
            .put("messageId", esbSmsRequest.getEsbHeader().getMsgId())
            .put("destination", esbSmsRequest.getEsbBody().getMobileNumber())
            .put("sender", serviceInfo.getUdf3())
            .put("keyword", serviceInfo.getUdf6())
            .put("shortMessage", esbSmsRequest.getEsbBody().getContent())
            .put("encryptMessage", "")
            .put("isEncrypt", "0")
            .put("type", "0")
            .put("requestTime", vnpUtils.getTimeLong(new Date()))
            .put("partnerCode", serviceInfo.getUdf2())
            .put("sercretKey", serviceInfo.getUdf1());

        String urlLogin = serviceInfo.getConnectorURL() ;
        ResponseEntity<String> resLogin = RequestUtils.executePostReq(restTemplate, urlLogin, jsonObject.toString());
        String body = resLogin.getBody();

        ExecuteModel<String> executeModel = ExecuteModel.<String>builder()
            .statusCode(Long.valueOf(resLogin.getStatusCode().value()))
            .message(body)
            .build();


        return executeModel;
    }
}
