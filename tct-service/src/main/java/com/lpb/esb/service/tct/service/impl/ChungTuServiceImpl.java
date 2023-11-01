package com.lpb.esb.service.tct.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.model.request.dto.*;
import com.lpb.esb.service.tct.service.ChungTuService;
import com.lpb.esb.service.tct.util.BuildMessageUtils;
import com.lpb.esb.service.tct.util.LogicUtils;
import com.lpb.esb.service.tct.util.RequestUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tudv1 on 2022-03-02
 */
@Service
@Log4j2
public class ChungTuServiceImpl implements ChungTuService {
    @Autowired
    RestTemplate restTemplateLB;
    @Autowired
    BuildMessageUtils buildMessageUtils;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    RequestUtils requestUtils;

    @Override
    public ResponseModel sendData(RequestData requestData) {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB
            , requestData.getEsbHeader().getServiceId()
            , requestData.getEsbHeader().getProductCode()
            , requestData.getEsbHeader().getHasRole()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(requestData)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);

        // Build message XML
        String header = buildMessageUtils.buildTctHeader(requestData.getTctHeader());
        String body = buildMessageUtils.buildBodyNopChungTu(requestData);
        String dataRequest = buildMessageUtils.buildMessageRequest(header, body);

        // Make request to T2B
        ExecuteModel<String> response = requestUtils.callAPI(dataRequest, serviceInfo);
        log.info("Res: [{}]\t{}", response.getStatusCode(), response.getData());

        if (response.getStatusCode().intValue() == 200) {
            //        String response = "";
            String responseXml = response.getData();

            // Build message response
            JSONObject jsonObject = logicUtils.parseDataFromXml(responseXml);
            try {
                jsonObject.getJSONObject("DATA").remove("SECURITY");
            } catch (Exception e) {
            }
            jsonObject.put("TCT_DATA", jsonObject.remove("DATA"));

            JSONObject out = logicUtils.recursiveJsonKeyConverterToLower(jsonObject);

            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(out.toMap())
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(response.getData())
                .build();
            return responseModel;
        }
    }

    @Override
    public ResponseModel huyChungTu(RequestData requestData) {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB
            , requestData.getEsbHeader().getServiceId()
            , requestData.getEsbHeader().getProductCode()
            , requestData.getEsbHeader().getHasRole()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(requestData)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);

        // Build message XML
        String header = buildMessageUtils.buildTctHeader(requestData.getTctHeader());
        String body = buildMessageUtils.buildBodyHuyChungTu(requestData);
        String dataRequest = buildMessageUtils.buildMessageRequest(header, body);

        // Make request to T2B
        ExecuteModel<String> response = requestUtils.callAPI(dataRequest, serviceInfo);
        log.info("Res: [{}]\t{}", response.getStatusCode(), response.getData());

        if (response.getStatusCode().intValue() == 200) {
            //        String response = "";
            String responseXml = response.getData();

            // Build message response
            JSONObject jsonObject = logicUtils.parseDataFromXml(responseXml);
            try {
                jsonObject.getJSONObject("DATA").remove("SECURITY");
            } catch (Exception e) {
            }
            jsonObject.put("TCT_DATA", jsonObject.remove("DATA"));

            JSONObject out = logicUtils.recursiveJsonKeyConverterToLower(jsonObject);

            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(out.toMap())
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(response.getData())
                .build();
            return responseModel;
        }
    }

    @Override
    public ResponseModel truyvan(EsbTctRequestDTO requestData) throws Exception {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB
            , requestData.getHeader().getServiceId()
            , requestData.getHeader().getProductCode()
            , requestData.getHeader().getOperation()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(requestData)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        TctRequestDTO tctRequestDTO = objectMapper.readValue(objectMapper.writeValueAsString(requestData.getBody()), TctRequestDTO.class);

        // Build message XML
        String header = buildMessageUtils.buildTctDTOHeader(tctRequestDTO.getTctHeader());
        String body = buildMessageUtils.buildBodyTruyVanChungTu(tctRequestDTO);
        String dataRequest = buildMessageUtils.buildMessageRequest(header, body);

        // Make request to T2B
        ExecuteModel<String> response = requestUtils.callAPI(dataRequest, serviceInfo);
        log.info("Res: [{}]\t{}", response.getStatusCode(), response.getData());

        if (response.getStatusCode().intValue() == 200) {
            //        String response = "";
            String responseXml = response.getData();

            // Build message response
            JSONObject jsonObject = logicUtils.parseDataFromXml(responseXml);
            try {
                jsonObject.getJSONObject("DATA").remove("SECURITY");
            } catch (Exception e) {
            }
            jsonObject.put("TCT_DATA", jsonObject.remove("DATA"));

            JSONObject out = logicUtils.recursiveJsonKeyConverterToLower(jsonObject);

            JSONObject jsonHeader = out.getJSONObject("tct_data").getJSONObject("header");

            Map<String, Object> mapData = new HashMap<>();
            Object o = out.getJSONObject("tct_data").getJSONObject("body").get("row");
            if (o instanceof JSONObject) {
                mapData.putAll(((JSONObject) o).toMap());
            } else if (o instanceof JSONArray) {
                JSONObject jo = new JSONObject();
                jo.put("list", (JSONArray) o);
                mapData.putAll(jo.toMap());
            }

//                EsbBillInfoDTO billInfo = objectMapper.readValue(jsonHeader.toString(), EsbBillInfoDTO.class);
            TctHeaderDTO tctHeaderDTO = objectMapper.readValue(jsonHeader.toString(), TctHeaderDTO.class);
//                TctBodyDTO tctBodyDTO = objectMapper.readValue(jsonHeader.toString(), TctBodyDTO.class);
            EsbTctBodyDTO bodyResponse = EsbTctBodyDTO.builder()
                .tctHeader(tctHeaderDTO)
//                    .tctBody(tctBodyDTO)
                .data(mapData)
                .build();

            EsbTctResponseDTO esbResponse = EsbTctResponseDTO.builder()
                .header(requestData.getHeader())
                .body(bodyResponse)
                .build();

            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            JSONObject dataJson = new JSONObject(mapper.writeValueAsString(esbResponse));

            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(dataJson.toMap())
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(response.getData())
                .build();
            return responseModel;
        }
    }
}


