package com.lpb.esb.service.query.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.query.model.queryV2.EsbReqDTO;
import com.lpb.esb.service.query.service.QueryServiceV2;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class QueryServiceV2Impl implements QueryServiceV2 {

    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel search(EsbReqDTO data) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB
                , data.getHeader().getServiceId()
                , data.getHeader().getProductCode()
                , data.getHeader().getOperation());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String response = RequestUtils.executePostReq(restTemplateLB, serviceInfo.get(0).getUrlApi(), objectMapper.writeValueAsString(data)).getBody();
            log.info(data.getHeader().getMsgId() + "_" + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("res_code");
            JSONObject dataRes = jsonResponse.getJSONObject("data");
            LpbResCode lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(dataRes.toMap())
                .build();
            return responseModel;
        } catch (RestClientException e) {
            log.info(data.getHeader().getMsgId() + "_" + e.getMessage());
            String errorRes = e.getMessage().replace("400 : ", "").trim();
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


            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
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
}
