package com.lpb.esb.service.sms.process.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.common.utils.rsa.RSAUtil;
import com.lpb.esb.service.sms.model.config.SmsFtelConfig;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.utils.EncryptMsgVtUtils;
import com.lpb.esb.service.sms.utils.LogicUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * Created by tudv1 on 2022-05-16
 */
@Component
@Log4j2
public class SmsSendFtelProcessImpl {
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    SmsFtelConfig smsFtelConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    EncryptMsgVtUtils encryptMsgVtUtils;
//    @Autowired
//    RequestUtils requestUtils;


    public ExecuteModel<String> loginFtel(ServiceInfo serviceInfo, EsbSmsRequest esbSmsRequest) {
        JSONObject jsonObject = new JSONObject()
            .put("grant_type", serviceInfo.getMethodAction())
            .put("client_id", serviceInfo.getUdf1())
//            .put("client_id", "3582480804ab687A9a695ABdcc26b184de7a659B")
            .put("client_secret", serviceInfo.getUdf2())
//            .put("client_secret", "5ed0e49f9d5f055749d020639c2e2d88B15e4cB0883bb4ea611cfd3620A2089b80d52f06")
            .put("scope", serviceInfo.getUdf4())
            .put("session_id", esbSmsRequest.getEsbHeader().getMsgId());

        String urlLogin = serviceInfo.getConnectorURL() + smsFtelConfig.getPathFptLogin();
        ResponseEntity<String> resLogin = RequestUtils.executePostReq(restTemplate, urlLogin, jsonObject.toString());

        String body = resLogin.getBody();

        ExecuteModel<String> executeModel = ExecuteModel.<String>builder()
            .statusCode(Long.valueOf(resLogin.getStatusCode().value()))
            .message(body)
            .build();


        return executeModel;
    }

    public boolean checkIsViettel(ServiceInfo serviceInfo, String phoneNumber, String token) {
        try {
            //check phone is viettel
            JSONObject vtcheck = new JSONObject()
                .put("access_token", token)
                .put("phone", phoneNumber);

            String urlCheckVT = serviceInfo.getConnectorURL() + smsFtelConfig.getPathCheckIsViettel();

            ResponseEntity<String> rsCheck = RequestUtils.executePostReq(restTemplate, urlCheckVT, vtcheck.toString());
            if (Integer.valueOf(rsCheck.getStatusCode().value()) == 200) {
                String bodyCheck = rsCheck.getBody();
                JSONObject isVt = new JSONObject(bodyCheck);
                Integer code = isVt.getInt("code");
                String message = isVt.getString("message");
                if (code == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public ExecuteModel sendFtelSms(ServiceInfo serviceInfo, EsbSmsRequest esbSmsRequest, String token) {
        String phoneNumber = esbSmsRequest.getEsbBody().getMobileNumber();
        boolean isViettel = checkIsViettel(serviceInfo, phoneNumber, token);

        String message = "";
//        String message = RSAUtil.encodeBase64(
//            esbSmsRequest.getEsbBody().getContent()
//                .getBytes(StandardCharsets.UTF_8)
//        ).replaceAll("\n", "").replaceAll("\r", "");

        if (isViettel == true) {
            try {
                if (serviceInfo.getProductCode().contains("SEND_BANCHNAME_OTP_LPB")){
                    message = encryptMsgVtUtils.encryptMessage(esbSmsRequest.getEsbBody().getContent(), smsFtelConfig.getInitVec(), smsFtelConfig.getPriCp(), smsFtelConfig.getPubCp());
                } else {
                    message = encryptMsgVtUtils.encryptMessage(esbSmsRequest.getEsbBody().getContent(), smsFtelConfig.getInitVec(), smsFtelConfig.getPriCpvv(), smsFtelConfig.getPubCpvv());
                }
                message = RSAUtil.encodeBase64(
                    message
                        .getBytes(StandardCharsets.UTF_8)
                ).replaceAll("\n", "").replaceAll("\r", "");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            message = RSAUtil.encodeBase64(
                esbSmsRequest.getEsbBody().getContent()
                    .getBytes(StandardCharsets.UTF_8)
            ).replaceAll("\n", "").replaceAll("\r", "");
        }

        JSONObject jsonObject = new JSONObject()
            .put("access_token", token)
            .put("session_id", esbSmsRequest.getEsbHeader().getMsgId())
            .put("BrandName", serviceInfo.getUdf3())
            .put("Phone", phoneNumber)
            .put("Message", message)
            .put("Encrypted", esbSmsRequest.getEsbBody().getServiceLog())
            .put("RequestId", esbSmsRequest.getEsbBody().getCategory());

        String urlLogin = serviceInfo.getConnectorURL() + smsFtelConfig.getPathFptPushBranchName();
        ResponseEntity<String> resLogin = RequestUtils.executePostReq(restTemplate, urlLogin, jsonObject.toString());
        String body = resLogin.getBody();

        ExecuteModel<String> executeModel = ExecuteModel.<String>builder()
            .statusCode(Long.valueOf(resLogin.getStatusCode().value()))
            .message(body)
            .build();


        return executeModel;
    }
}
