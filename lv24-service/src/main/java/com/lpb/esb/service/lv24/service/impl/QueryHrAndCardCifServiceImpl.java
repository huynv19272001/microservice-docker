//package com.lpb.esb.service.lv24.service.impl;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lpb.esb.service.common.model.response.LpbResCode;
//import com.lpb.esb.service.common.model.response.ResponseModel;
//import com.lpb.esb.service.common.utils.code.EsbErrorCode;
//import com.lpb.esb.service.lv24.config.IbmSqlServiceConfig;
//import com.lpb.esb.service.lv24.config.RestTemplateConfig;
//import com.lpb.esb.service.lv24.exception.validation.FieldValidator;
//import com.lpb.esb.service.lv24.model.EsbRequestDTO;
//import com.lpb.esb.service.lv24.model.corehr.CoreHrRequest;
//import com.lpb.esb.service.lv24.model.corehr.CoreRequest;
//import com.lpb.esb.service.lv24.model.corehr.EsbResponse;
//import com.lpb.esb.service.lv24.model.corehr.HrResponse;
//import com.lpb.esb.service.lv24.service.QueryHrAndCardCifService;
//import com.lpb.esb.service.lv24.util.SqlServiceClient;
//import com.lpb.middleware.sqlservice.*;
//import lombok.SneakyThrows;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.util.Arrays;
//
//@Log4j2
//@Service
//public class QueryHrAndCardCifServiceImpl implements QueryHrAndCardCifService {
//    @Autowired
//    RestTemplateConfig restTemplate;
//    @Autowired
//    FieldValidator fieldValidator;
//    @Autowired
//    SqlServiceClient sqlServiceClient;
//    @Autowired
//    IbmSqlServiceConfig ibmSqlServiceConfig;
//
//    @SneakyThrows
//    @Override
//    public ResponseModel callTwoApis(EsbRequestDTO esbRequest) {
//        fieldValidator.validate(esbRequest);
//        String msgId = esbRequest.getHeader().getMsgId();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ResponseModel responseModel;
//        LpbResCode lpbResCode;
//
//        log.info("Message ID: " + msgId + " | ESB REQ: " + objectMapper.writeValueAsString(esbRequest));
//
//        CoreHrRequest esbRequestData = objectMapper.readValue(objectMapper.writeValueAsString(esbRequest.getBody().getData()), CoreHrRequest.class);
//
//        HrResponse hrResponse = getHrResponse(esbRequestData, msgId);
//        JsonNode coreResponse = getCoreResponse(esbRequestData, msgId);
//        EsbResponse esbResponse = EsbResponse.builder()
//            .core(coreResponse)
//            .hr(hrResponse)
//            .build();
//
//        lpbResCode = LpbResCode.builder()
//            .errorCode(EsbErrorCode.SUCCESS.label)
//            .errorDesc("Service Success")
//            .build();
//
//        responseModel = ResponseModel.builder()
//            .resCode(lpbResCode)
//            .data(esbResponse)
//            .build();
//
//        return responseModel;
//    }
//
//    private HrResponse getHrResponse(CoreHrRequest esbRequestData, String msgId){
//        //set headers
//        FCUBSHEADERINFO fcubsHeader = new FCUBSHEADERINFO();
//        fcubsHeader.setMSGID(msgId);
//        fcubsHeader.setUSERID(ibmSqlServiceConfig.getQueryHrLv24VUserId());
//        fcubsHeader.setSERVICE(ibmSqlServiceConfig.getQueryHrLv24VService());
//        fcubsHeader.setOPERATION(ibmSqlServiceConfig.getQueryHrLv24VOperation());
//        fcubsHeader.setPASSWORD(ibmSqlServiceConfig.getQueryHrLv24VPassword());
//        //set body
//        SERVICEIO serviceIo = new SERVICEIO();
//        serviceIo.setSERVICEID(ibmSqlServiceConfig.getQueryHrLv24VServiceId());
//        serviceIo.setPRODUCTCODE(ibmSqlServiceConfig.getQueryHrLv24VProductCode());
//
//        PARAMINFO paramInfo = new PARAMINFO();
//        paramInfo.setNAME("TKVI");
//        paramInfo.setVALUE(esbRequestData.getTkvi());
//
//        ADDLINFO addlInfo = new ADDLINFO();
//        addlInfo.getPARAM().add(paramInfo);
//
//        DMLBODYINFO dmlBodyInfo = new DMLBODYINFO();
//        dmlBodyInfo.setSERVICEIO(serviceIo);
//        dmlBodyInfo.setADDL(addlInfo);
//
//        //set request
//        DMLCOMMANDREQ request = new DMLCOMMANDREQ();
//        request.setFCUBSHEADER(fcubsHeader);
//        request.setDMLBODYINFO(dmlBodyInfo);
//
//        DMLCOMMANDRES response = sqlServiceClient.dmlRequest(ibmSqlServiceConfig.getQueryHrLv24VUrl(), request);
//
//        HrResponse hrResponse;
//        if(response.getDMLBODYINFO().getFCUBSERRORRESP().getERROR().get(0).getECODE().equals("ESB-000")){
//            hrResponse = HrResponse.builder()
//                .rows(response.getDMLBODYINFO().getROWS())
//                .fcubsErrorResp(response.getDMLBODYINFO().getFCUBSERRORRESP())
//                .build();
//        }
//        else{
//            hrResponse = HrResponse.builder()
//                .fcubsErrorResp(response.getDMLBODYINFO().getFCUBSERRORRESP())
//                .build();
//        }
//
//        return hrResponse;
//    }
//
//    @SneakyThrows
//    private JsonNode getCoreResponse(CoreHrRequest esbRequestData, String msgId){
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        CoreRequest coreRequest = CoreRequest.builder()
//            .cif(esbRequestData.getCif())
//            .msgId(msgId)
//            .build();
//
//        log.info("Message ID: " + msgId + " | CORE REQ: " + objectMapper.writeValueAsString(coreRequest));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(coreRequest), headers);
//
//        String response;
//        try {
//            response = restTemplate.getRestTemplateLB().exchange("http://esb-card-service/api/v1/card/check-late-dat-card-by-cif", HttpMethod.POST, entity, String.class).getBody();
//        }
//        catch (HttpClientErrorException exception){
//            response = exception.getResponseBodyAsString();
//        }
//        JsonNode jsonResponse = objectMapper.readTree(response);
//
//        return jsonResponse;
//    }
//}
