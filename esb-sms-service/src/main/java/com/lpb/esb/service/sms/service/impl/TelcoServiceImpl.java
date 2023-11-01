package com.lpb.esb.service.sms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.sms.model.dto.TelcoBodyDTO;
import com.lpb.esb.service.sms.model.dto.TelcoHeaderDTO;
import com.lpb.esb.service.sms.model.dto.TelcoPushDTO;
import com.lpb.esb.service.sms.model.dto.TelcoUpdateDTO;
import com.lpb.esb.service.sms.model.entities.MidTelcoUpdateEntity;
import com.lpb.esb.service.sms.model.request.TelcoRequest;
import com.lpb.esb.service.sms.repositories.MidTelcoUpdateRepository;
import com.lpb.esb.service.sms.service.TelcoService;
import com.lpb.esb.service.sms.utils.LogicUtils;
import oracle.sql.TIMESTAMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelcoServiceImpl implements TelcoService {

    private String sql_service_url = "http://sql-service/api/v1/execute-process";
    @Autowired
    MidTelcoUpdateRepository midTelcoUpdateRepository;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    RestTemplate restTemplateLoadBalancer;
    @Override
    public ResponseModel updateTelco(TelcoRequest telcoRequest) {

        MidTelcoUpdateEntity midTelcoUpdateEntity = MidTelcoUpdateEntity.builder()
            .mobileNo(telcoRequest.getMobileNo())
            .telcoCode(telcoRequest.getTelcoCode())
            .sentTime(logicUtils.formatDate(telcoRequest.getSentTime()))
            .sender(telcoRequest.getSender())
            .keyword(telcoRequest.getKeyWord())
            .messageId(telcoRequest.getMessageId())
            .status(telcoRequest.getStatus())
            .build();
        try {
            TelcoHeaderDTO telcoHeaderDTO = TelcoHeaderDTO.builder()
                .msgId(telcoRequest.getMessageId())
                .source("VNPAY")
                .serviceId("050001")
                .operation(telcoRequest.getSender())
                .productCode("SEND_SMS_BANCHNAME_VNP_LPB")
                .build();

            List<TelcoUpdateDTO> list = new ArrayList<>();
            list.add(new TelcoUpdateDTO("p_mobile_no", telcoRequest.getMobileNo()));
            list.add(new TelcoUpdateDTO("p_telcocode", telcoRequest.getTelcoCode()));
            list.add(new TelcoUpdateDTO("p_time_update_telco", midTelcoUpdateEntity.getSentTime()));

            TelcoBodyDTO telcoBodyDTO = TelcoBodyDTO.builder()
                .params(list)
                .build();

            TelcoPushDTO telcoPushDTO = TelcoPushDTO.builder()
                .header(telcoHeaderDTO)
                .body(telcoBodyDTO)
                .build();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseEntity<String> resLogin = RequestUtils.executePostReq(restTemplateLoadBalancer, sql_service_url, objectMapper.writeValueAsString(telcoPushDTO));


//            ObjectMapper objectMapper = new ObjectMapper();
//            ResponseEntity<String> resLogin = RequestUtils.executePostReq(restTemplateLoadBalancer, sql_service_url, objectMapper.writeValueAsString(telcoPushDTO));
//            String body = resLogin.getBody();
            if (resLogin.getStatusCode().value() == 200){
                midTelcoUpdateRepository.save(midTelcoUpdateEntity);
                ResponseModel responseModel = ResponseModel.builder()
                    .errorCode(EsbErrorCode.SUCCESS.label)
                    .errorDesc("Success")
                    .build();
                return responseModel;
            } else {
                ResponseModel responseModel = ResponseModel.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("Failed")
                    .build();
                return responseModel;
            }
        } catch (Exception e) {
            ResponseModel responseModel = ResponseModel.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            return responseModel;
        }
    }
}
