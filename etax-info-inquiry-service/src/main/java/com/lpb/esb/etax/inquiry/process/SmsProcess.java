/**
 * @author Trung.Nguyen
 * @date 01-Nov-2022
 * */
package com.lpb.esb.etax.inquiry.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.etax.inquiry.config.ServiceApiConfig;
import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.data.SmsInfo;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;
import com.lpb.esb.etax.inquiry.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.Collections;

@Component
@Log4j2
public class SmsProcess {

    @Autowired
    private ServiceApiConfig serviceApiConfig;
    @Autowired
    private RestClientUtils restClientUtils;

    public DefaultResponse sendSms(SmsInfo smsInfo) {
        DefaultResponse response = new DefaultResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Create JSON headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // Build the rest request
            HttpEntity<SmsInfo> restRequest = new HttpEntity<>(smsInfo, headers);
            // Call Rest API
            ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getSmsgwCategoryUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("send-sms, error: He thong khong nhan duoc response");
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("send-sms, response body: " + body);
            ResponseModel smsGwCategoryRes = objectMapper.readValue(body, ResponseModel.class);
            // Check response object
            if ((smsGwCategoryRes == null) || (smsGwCategoryRes.getResCode() == null)) {
                log.error("send-sms, error: Response khong hop le");
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Check response code
//            log.info("send-sms, debug: " + smsGwCategoryRes);
            if (!"ESB-000".equals(smsGwCategoryRes.getResCode().getErrorCode())) {
                response.setResponseCode(smsGwCategoryRes.getResCode().getErrorCode());
                response.setResponseDesc(smsGwCategoryRes.getResCode().getErrorDesc());
                return response;
            }
            // return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("send-sms, exception: "+ ex.getMessage());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("send-sms, exception: "+ ex.getMessage());
        }
        return response;
    }
}
