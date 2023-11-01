package com.lpb.esb.service.query.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.query.model.EsbBillInfoDTO;
import com.lpb.esb.service.query.model.EsbBodyInfoDTO;
import com.lpb.esb.service.query.model.EsbRequestDTO;
import com.lpb.esb.service.query.model.EsbResponseDTO;
import com.lpb.esb.service.query.model.tct.EsbHeader;
import com.lpb.esb.service.query.model.tct.RequestBody;
import com.lpb.esb.service.query.model.tct.RequestData;
import com.lpb.esb.service.query.model.tct.TctHeader;
import com.lpb.esb.service.query.service.QueryService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class TCTServiceImpl implements QueryService {
    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel search(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB, data.getHeader().getServiceId(), data.getHeader().getProductCode(), "TCTService");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(initRequestData(data)), headers);

//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

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

                Map<String, Object> mapData = new HashMap<>();
                Object o = jsonResponse.getJSONObject("data").getJSONObject("tct_data").getJSONObject("body").get("row");
                if (o instanceof JSONObject) {
                    mapData.putAll(((JSONObject) o).toMap());
                } else if (o instanceof JSONArray) {
                    JSONObject jo = new JSONObject();
                    jo.put("list", (JSONArray) o);
                    mapData.putAll(jo.toMap());
                }

                EsbBillInfoDTO billInfo = objectMapper.readValue(jsonHeader.toString(), EsbBillInfoDTO.class);
                EsbBodyInfoDTO bodyResponse = EsbBodyInfoDTO.builder()
                    .billInfo(billInfo)
                    .data(mapData)
                    .build();

                EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                    .header(data.getHeader())
                    .body(bodyResponse)
                    .build();

                ObjectMapper mapper = new ObjectMapper();
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                ;

                JSONObject dataJson = new JSONObject(mapper.writeValueAsString(esbResponse));
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
            .senderCode(data.getBody().getBillInfo().getSenderCode())
            .senderName(data.getBody().getBillInfo().getSenderName())
            .receiverCode(data.getBody().getBillInfo().getReceiverCode())
            .receiverName(data.getBody().getBillInfo().getReceiverName())
            .tranCode(data.getBody().getBillInfo().getTranCode())
            .msgId(data.getHeader().getMsgId())
            .sendDate(data.getBody().getBillInfo().getSendDate())
            .originalCode(data.getBody().getBillInfo().getOriginalCode())
            .originalName(data.getBody().getBillInfo().getOriginalName())
            .originalDate(data.getBody().getBillInfo().getOriginalDate()).build();

        RequestBody requestBody = RequestBody.builder().build();
        switch (data.getHeader().getProductCode()) {
            case "TCT_QUERY_LPTB":
                requestBody = RequestBody.builder()
                    .soToKhai(data.getBody().getBillInfo().getBillCode() != null ? data.getBody().getBillInfo().getBillCode() : "")
                    .maSoThue(data.getBody().getBillInfo().getBillId() != null ? data.getBody().getBillInfo().getBillId() : "")
                    .build();
                break;
            case "TCT_QUERY_TND":
                requestBody = RequestBody.builder()
                    .maHs(data.getBody().getBillInfo().getBillId())
                    .so(data.getBody().getBillInfo().getBillCode())
                    .build();
                break;
            case "TCT_QUERY_TCN":
                requestBody = RequestBody.builder()
                    .maSoThue(data.getBody().getBillInfo().getBillCode())
                    .maCqThu(data.getBody().getBillInfo().getBillId())
                    .loaiThue(data.getBody().getBillInfo().getBillType())
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
}
