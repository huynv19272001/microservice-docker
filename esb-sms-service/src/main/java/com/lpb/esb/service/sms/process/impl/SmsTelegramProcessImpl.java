package com.lpb.esb.service.sms.process.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import com.lpb.esb.service.sms.process.SmsSendProcess;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2021-08-13
 */
@Component("smsTelegramProcess")
@Log4j2
@RefreshScope
public class SmsTelegramProcessImpl implements SmsSendProcess {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.sms.telegram:https://api.telegram.org/bot1889355399:AAEkD07qKJbyCF7WrmP4wjF_16bkoTRktJQ/sendMessage}")
    private String apiTele;

    @Override
    public ExecuteModel sendMessage(String message, String chatId) {
        JSONObject jsonObject = new JSONObject()
            .put("chat_id", chatId)
            .put("text", message);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), httpHeaders);

            ResponseEntity<String> response = restTemplate.postForEntity(apiTele, request, String.class);
            String body = response.getBody();
            ExecuteModel executeModel = ExecuteModel.builder()
                .executeCode(ExecuteCode.SUCCESS)
                .data(new JSONObject(body).toMap())
                .build();
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                executeModel.setExecuteCode(ExecuteCode.SUCCESS);
            }
            return executeModel;
        } catch (RestClientException e) {
            log.error("error when execute request: {}", e.getMessage(), e);
            ExecuteModel executeModel = ExecuteModel.builder()
                .executeCode(ExecuteCode.FAIL)
                .data(e.getMessage())
                .build();
            return executeModel;
        }
    }

    @Override
    public ExecuteModel sendMessage(MsgInputTempEntity msgInputTempEntity) {
        return null;
    }
}
