package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.entities.EsbErrorPartnerMessageEntity;
import com.esb.card.dto.telcoRequest.*;
import com.esb.card.dto.telcoResponse.EsbTelcoResDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.respository.EsbCardCoreUserInfoRepository;
import com.esb.card.respository.EsbErrorPartnerMessageRepository;
import com.esb.card.service.TelcoService;
import com.esb.card.utils.BuildMessageUtils;
import com.esb.card.utils.LogicUtils;
import com.esb.card.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cuongnm on 2022-08-03
 */
@Service
@Log4j2
public class TelcoServiceImpl implements TelcoService {

    private String sql_service_url = "http://sql-service/api/v1/execute-process";
    @Autowired
    EsbCardCoreUserInfoRepository esbCardCoreUserInfoRepository;
    @Autowired
    RestTemplate restTemplateLoadBalancer;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    BuildMessageUtils buildMessageUtils;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    EsbErrorPartnerMessageRepository esbErrorPartnerMessageRepository;

    @Override
    public ResponseModel updateTelco(EsbTelCoReqDTO telcoRequest) {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
            , Constant.SERVICE_ID, Constant.PRODUCT_UPDATE_TELCO_CODE, Constant.HAS_ROLE
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
//                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        if (telcoRequest.getPhoneNo() == null || telcoRequest.getTelCode() == null || telcoRequest.getPhoneNo().equals("") || telcoRequest.getTelCode().equals("")) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("Input data could not be null or empty")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
//                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        if (telcoRequest.getPhoneNo() == null) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("Input data could not be null or empty")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
//                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        ServiceInfo serviceInfo = list.get(0);
        String Phoneno = logicUtils.checkValidPhone(logicUtils.phone84To0(telcoRequest.getPhoneNo()));
        if (Phoneno.equals("")) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("PhoneNo not valid")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
//                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        try {
            // update web back office
            ObjectMapper objectMapper = new ObjectMapper();
            EsbCardCoreUserInfo entity = esbCardCoreUserInfoRepository.findById(serviceConfig.getUserCardCore()).get();
            String messageBody = buildMessageUtils.buildTelcoMsgXml(telcoRequest, Phoneno);
            EsbTelCoRequestDTO esbTelCoRequestDTO = buildMessageUtils.buildTelcoRequest(serviceInfo, entity, messageBody);
            ResponseEntity<String> telcoRes = RequestUtils.executePostReq(restTemplate, serviceInfo.getUrlApi() + serviceInfo.getConnectorURL(), objectMapper.writeValueAsString(esbTelCoRequestDTO));
            log.info(telcoRes.getBody());
            EsbTelcoResDTO esbTelcoResDTO = objectMapper.readValue(telcoRes.getBody(), EsbTelcoResDTO.class);
            EsbErrorPartnerMessageEntity errorPartnerMessage = esbErrorPartnerMessageRepository.getErrorMessage(Constant.SERVICE_ID, "TELCO", String.valueOf(esbTelcoResDTO.getHeader().getHttpStatusCode()));
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(errorPartnerMessage.getErrorCode())
                .errorDesc(errorPartnerMessage.getDescription())
                .build();

            // update vvadmin
            TelcoHeaderDTO telcoHeaderDTO = TelcoHeaderDTO.builder()
                .msgId("update telco code")
                .source("TELCO")
                .serviceId("900004")
                .branch("001")
                .operation("SQL_TCL")
                .productCode("INSERT_TELCOCODE_HISTORY")
                .build();

            List<TelcoUpdateDTO> telcoUpdateDTOList = new ArrayList<>();
            telcoUpdateDTOList.add(new TelcoUpdateDTO("mobile_no", Phoneno));
            telcoUpdateDTOList.add(new TelcoUpdateDTO("telcocode", telcoRequest.getTelCode()));
            telcoUpdateDTOList.add(new TelcoUpdateDTO("time_update_telco", logicUtils.convertDateToTimestamp(new Date().getTime())));

            TelcoBodyDTO telcoBodyDTO = TelcoBodyDTO.builder()
                .params(telcoUpdateDTOList)
                .build();

            TelcoPushDTO telcoPushDTO = TelcoPushDTO.builder()
                .header(telcoHeaderDTO)
                .body(telcoBodyDTO)
                .build();

            ResponseEntity<String> resSql = RequestUtils.executePostReq(restTemplateLoadBalancer, sql_service_url, objectMapper.writeValueAsString(telcoPushDTO));


//            ObjectMapper objectMapper = new ObjectMapper();
//            ResponseEntity<String> resSql = RequestUtils.executePostReq(restTemplateLoadBalancer, sql_service_url, objectMapper.writeValueAsString(telcoPushDTO));
//            String body = resSql.getBody();
            if (resSql.getStatusCode().value() == 200 && lpbResCode.getErrorCode().equals("ESB-000")) {
                ResponseModel responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data("Update Web Back Office and VVADMIN Success")
                    .build();
                return responseModel;
            } else if (resSql.getStatusCode().value() != 200 && lpbResCode.getErrorCode().equals("ESB-000")) {
                ResponseModel responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data("Update Web Back Office Success and VVADMIN Failed")
                    .build();
                return responseModel;
            } else if (resSql.getStatusCode().value() == 200 && !lpbResCode.getErrorCode().equals("ESB-000")) {
                ResponseModel responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data("Update Web Back Office Failed and VVADMIN Success")
                    .build();
                return responseModel;
            } else {
                lpbResCode = LpbResCode.builder()
                    .errorCode("ESB-099")
                    .errorDesc("Failed")
                    .build();
                ResponseModel responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data("Update Web Back Office and VVADMIN Failed")
                    .build();
                return responseModel;
            }

        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("Update Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return responseModel;
        }
    }
}
