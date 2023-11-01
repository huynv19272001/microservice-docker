package com.lpb.esb.service.gateway2.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.gateway2.model.constant.ServiceApiConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Created by tudv1 on 2021-08-19
 */
@Component
@Log4j2
public class EsbSystemLogProcess {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    ObjectMapper objectMapper;

    public ResponseModel<ExecuteModel<BigDecimal>> getNextSequenceSystemLog() throws JsonProcessingException {
        ResponseEntity<String> res = restTemplate.getForEntity(serviceApiConfig.getApiSequenceNext(), String.class);
        String body = res.getBody();
        ResponseModel<ExecuteModel<BigDecimal>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<ExecuteModel<BigDecimal>>>() {
        });
        log.info("Response [{}] for next seq: {}", res.getStatusCodeValue(), responseModel);
        return responseModel;
    }
}
