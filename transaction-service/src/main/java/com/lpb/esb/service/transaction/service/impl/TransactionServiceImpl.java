package com.lpb.esb.service.transaction.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.transaction.model.*;
import com.lpb.esb.service.transaction.model.tct.EsbHeader;
import com.lpb.esb.service.transaction.model.tct.RequestBody;
import com.lpb.esb.service.transaction.model.tct.RequestData;
import com.lpb.esb.service.transaction.model.tct.TctHeader;
import com.lpb.esb.service.transaction.service.TransactionService;
import com.lpb.esb.service.transaction.utils.ESBUtils;
import com.lpb.esb.service.transaction.utils.TransactionUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    RestTemplate restTemplateLB;

    @Autowired
    TransactionUtils transactionUtils;

    @Override
    public ResponseModel payment(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();
        return responseModel;
    }

    @Override
    public ResponseModel bill(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB, data.getHeader().getServiceId(), data.getHeader().getProductCode(), "TCTService");
            String app_id = transactionUtils.getNextSequenceSystemLog().getData().getData().toString();

            log.info(data.getHeader().getMsgId() + "_" + app_id + "_"
                + data.getBody().getTransactionInfo().getBranch() + "_"
                + ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()) + "_"
                + ESBUtils.createXmlPartner(data) + "_"
                + ESBUtils.createXmlServiceDTO(data));

            ExecuteModel executeModel = this.transactionUtils.requestTransaction("ESB_GATEWAY",
                data.getBody().getTransactionInfo().getBranch(),
                data.getBody().getTransactionInfo().getTranDesc(),
                app_id, "Y",
                "REQUEST_TXN",
                ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()),
                ESBUtils.createXmlPartner(data),
                ESBUtils.createXmlServiceDTO(data));

            if (!executeModel.getExecuteCode().equals(ExecuteCode.SUCCESS)) {
                log.info(data.getHeader().getMsgId() + "_" + executeModel.getExecuteCode() + "_" + executeModel.getStatusCode() + "_" + executeModel.getMessage());
                resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("RequestTransaction fail!")
                    .build();

                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .data(executeModel)
                    .build();
                return responseModel;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(initRequestData(data)), headers);
            String response = restTemplateLB.exchange
                (serviceInfo.get(0).getUrlApi(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("res_code");
            LpbResCode lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            if (!lpbResCode.getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .build();

                JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(dataJson.toMap())
                    .build();
            } else {
                try {
                    String jsonErrorDesc = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("error").getString("error_desc");
                    if (!jsonErrorDesc.isEmpty()) {
                        resCode = LpbResCode.builder()
                            .errorCode(EsbErrorCode.FAIL.label)
                            .errorDesc(jsonErrorDesc)
                            .build();

                        EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                            .header(data.getHeader())
                            .build();
                        JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));

                        responseModel = ResponseModel.builder()
                            .resCode(resCode)
                            .data(dataJson.toMap())
                            .build();
                        return responseModel;
                    }
                } catch (Exception e) {
                }
                JSONObject jsonHeader = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("header");
                JSONObject jsonData = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("body").getJSONObject("row");

                EsbTransactionDTO transactionDTO = objectMapper.readValue(jsonHeader.toString(), EsbTransactionDTO.class);
                EsbBodyInfoDTO bodyResponse = EsbBodyInfoDTO.builder()
                    .transactionInfo(transactionDTO)
                    .data(jsonData.toMap())
                    .build();

                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .body(bodyResponse)
                    .build();

                JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .data(dataJson.toMap())
                    .build();
            }
            return responseModel;
        } catch (RestClientException e) {
            log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
            String errorRes = e.getMessage().replace("400 : ", "").trim();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = (JsonArray) jsonParser.parse(errorRes);
                JsonElement resErrorCode = jsonArray.get(0).getAsJsonObject().get("res_code").getAsJsonObject().get("error_code");
                JsonElement resErrorDesc = jsonArray.get(0).getAsJsonObject().get("res_code").getAsJsonObject().get("error_desc");
                JsonElement resDataJson = jsonArray.get(0).getAsJsonObject().get("data");

                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(resErrorCode.getAsString())
                    .errorDesc(resErrorDesc.getAsString())
                    .build();
                lpbResCode.setRefDesc(resDataJson.getAsString());

                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .build();
                JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));

                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(dataJson.toMap())
                    .build();
            } catch (JsonProcessingException jsonProcessingException) {
                log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
                resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc(e.getMessage())
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(data.getHeader().getMsgId() + "_" + e.getMessage(), e);
            resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc(e.getMessage())
                .build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
        }
        return responseModel;
    }

    @Override
    public ResponseModel revert(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB, data.getHeader().getServiceId(), data.getHeader().getProductCode(), "TCTService");
            log.info(serviceInfo);
            String app_id = transactionUtils.getNextSequenceSystemLog().getData().getData().toString();
            log.info(app_id);

            log.info(data.getHeader().getMsgId() + "_" + app_id + "_"
                + data.getBody().getTransactionInfo().getBranch() + "_"
                + ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()) + "_"
                + ESBUtils.createXmlPartner(data) + "_"
                + ESBUtils.createXmlServiceDTO(data));

            ExecuteModel executeModel = this.transactionUtils.requestTransaction("ESB_GATEWAY",
                data.getBody().getTransactionInfo().getBranch(),
                data.getBody().getTransactionInfo().getTranDesc(),
                app_id, "Y",
                "REVERT_TXN",
                ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()),
                ESBUtils.createXmlPartner(data),
                ESBUtils.createXmlServiceDTO(data));

            if (!executeModel.getExecuteCode().equals(ExecuteCode.SUCCESS)) {
                log.info(data.getHeader().getMsgId() + "_" + executeModel.getExecuteCode() + "_" + executeModel.getStatusCode() + "_" + executeModel.getMessage());
                resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("RequestTransactionRevert fail!")
                    .build();

                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .data(executeModel)
                    .build();
                return responseModel;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(initRequestData(data)), headers);
            String response = restTemplateLB.exchange
                (serviceInfo.get(0).getUrlApi(), HttpMethod.DELETE, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("res_code");
            LpbResCode lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            if (!lpbResCode.getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .build();

                JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(dataJson.toMap())
                    .build();

            } else {
                try {
                    String jsonErrorDesc = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("error").getString("error_desc");
                    if (!jsonErrorDesc.isEmpty()) {
                        resCode = LpbResCode.builder()
                            .errorCode(EsbErrorCode.FAIL.label)
                            .errorDesc(jsonErrorDesc)
                            .build();

                        EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                            .header(data.getHeader())
                            .build();
                        JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));

                        responseModel = ResponseModel.builder()
                            .resCode(resCode)
                            .data(dataJson.toMap())
                            .build();
                        return responseModel;
                    }
                } catch (Exception e) {
                }

                JSONObject jsonHeader = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("header");
                JSONObject jsonData = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("body").getJSONObject("row");

                EsbTransactionDTO transactionDTO = objectMapper.readValue(jsonHeader.toString(), EsbTransactionDTO.class);
                EsbBodyInfoDTO bodyResponse = EsbBodyInfoDTO.builder()
                    .transactionInfo(transactionDTO)
                    .data(jsonData.toMap())
                    .build();

                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .body(bodyResponse)
                    .build();

                JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .data(dataJson.toMap())
                    .build();
            }
            return responseModel;
        } catch (Exception e) {
            log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
            resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc(e.getMessage())
                .build();

            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
        }

        return responseModel;
    }


    private RequestData initRequestData(EsbRequestDTO data) {
        EsbHeader esbHeader = EsbHeader.builder().serviceId(data.getHeader().getServiceId())
            .productCode(data.getHeader().getProductCode())
            .hasRole("TCTService").build();
        TctHeader tctHeader = TctHeader.builder().version("1.0")
            .senderCode(data.getBody().getTransactionInfo().getSenderCode())
            .senderName(data.getBody().getTransactionInfo().getSenderName())
            .receiverCode(data.getBody().getTransactionInfo().getReceiverCode())
            .receiverName(data.getBody().getTransactionInfo().getReceiverName())
            .tranCode(data.getBody().getTransactionInfo().getTranCode())
            .msgId(data.getHeader().getMsgId())
            .sendDate(data.getBody().getTransactionInfo().getSendDate())
            .originalCode(data.getBody().getTransactionInfo().getOriginalCode())
            .originalName(data.getBody().getTransactionInfo().getOriginalName())
            .originalDate(data.getBody().getTransactionInfo().getOriginalDate()).build();

        RequestBody requestBody = RequestBody.builder().build();
        switch (data.getHeader().getProductCode()) {
            case "TCT_CHUNGTU_NOP":
                requestBody = RequestBody.builder()
                    .row(new JSONObject(data.getBody().getBillThueInfo()).toMap())
                    .build();
                break;
            case "TCT_CHUNGTU_HUY":
                requestBody = RequestBody.builder()
                    .soChungtu(data.getBody().getBillInfo().getBillCode())
                    .maNhtm(data.getBody().getBillInfo().getBillId())
                    .build();
                break;
        }

        RequestData requestData = RequestData.builder()
            .esbHeader(esbHeader)
            .tctHeader(tctHeader)
            .body(requestBody)
            .build();
        return requestData;
    }

    @Override
    public ResponseModel billV2(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB, data.getHeader().getServiceId(), data.getHeader().getProductCode());
            String app_id = transactionUtils.getNextSequenceSystemLog().getData().getData().toString();

            log.info(data.getHeader().getMsgId() + "_" + app_id + "_"
                + data.getBody().getTransactionInfo().getBranch() + "_"
                + ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()) + "_"
                + ESBUtils.createXmlPartner(data) + "_"
                + ESBUtils.createXmlServiceDTO(data));

            ExecuteModel executeModel = this.transactionUtils.requestTransaction("ESB_GATEWAY",
                data.getBody().getTransactionInfo().getBranch(),
                data.getBody().getTransactionInfo().getTranDesc(),
                app_id, "Y",
                "REQUEST_TXN",
                ESBUtils.createXmlCustomer(data.getBody().getTransactionInfo().getCustomerNo()),
                ESBUtils.createXmlPartner(data),
                ESBUtils.createXmlServiceDTO(data));

            if (!executeModel.getExecuteCode().equals(ExecuteCode.SUCCESS)) {
                log.info(data.getHeader().getMsgId() + "_" + executeModel.getExecuteCode() + "_" + executeModel.getStatusCode() + "_" + executeModel.getMessage());
                resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("RequestTransaction fail!")
                    .build();

                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .data(executeModel)
                    .build();
                return responseModel;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data), headers);
            String response = restTemplateLB.exchange
                (serviceInfo.get(0).getUrlApi(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);

            EsbBodyInfoDTO bodyResponse = EsbBodyInfoDTO.builder()
                .transactionInfo(data.getBody().getTransactionInfo())
                .data(objectMapper.readTree(response).path("data"))
                .build();

            EsbResponseDTO esbResponse = EsbResponseDTO.builder()
//                .header(data.getHeader())
                .body(bodyResponse)
                .build();

            JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(dataJson.toMap())
                .build();

            return responseModel;
        } catch (RestClientException e) {
            log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
            String errorRes = e.getMessage().replace("400 : ", "").trim();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                errorRes = errorRes.replaceAll("\\[", "").replaceAll("\\]", "");
                log.info("ERROR RES: " + errorRes);
                responseModel = objectMapper.readValue(errorRes, ResponseModel.class);
            } catch (JsonProcessingException jsonProcessingException) {
                log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
                resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc(e.getMessage())
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(resCode)
                    .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(data.getHeader().getMsgId() + "_" + e.getMessage(), e);
            resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc(e.getMessage())
                .build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
        }
        return responseModel;
    }
}
