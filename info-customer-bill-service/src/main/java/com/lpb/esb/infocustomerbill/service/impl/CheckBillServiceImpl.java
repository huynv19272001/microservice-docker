package com.lpb.esb.infocustomerbill.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.infocustomerbill.service.CheckBillService;
import com.lpb.esb.service.common.model.request.infocustomerbill.DataQueryDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.HeaderDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class CheckBillServiceImpl implements CheckBillService {

    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel excuteCheckBill(DataQueryDTO REQ) {
        ResponseModel.builder().build();
        ResponseModel responseModel;
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        DataQueryDTO RES = new DataQueryDTO();
        HeaderDTO Header = REQ.getHeader();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            log.info(Header.getMsgid() + " Request: " + objectMapper.writeValueAsString(REQ));
//            Get thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, REQ.getBody().getService().getServiceId(), REQ.getBody().getService().getProductCode().toUpperCase(), REQ.getHeader().getOperation().toUpperCase());

            if (list.size() == 0) {
                log.info(Header.getMsgid() + " Error RQ CheckBill: 493 - Dịch vụ Nhà cung cấp không hợp lệ");
                lpbResCode.setErrorCode("493");
                lpbResCode.setErrorDesc("Dịch vụ Nhà cung cấp không hợp lệ");
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            ServiceInfo serviceInfo = list.get(0);

//            Get Request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            Header.setDestination(serviceInfo.getConnectorURL());
            REQ.setHeader(Header);
            log.info(Header.getMsgid() + " Request call partner : " + objectMapper.writeValueAsString(REQ));
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(REQ), headers);
            log.info(Header.getMsgid() + " Request call partner : " + entity);

//            Get response
            String response = restTemplateLB.exchange(serviceInfo.getUrlApi(), HttpMethod.POST, entity, String.class).getBody();
            log.info(Header.getMsgid() + " Response call partner : " + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
            JSONObject jsonData = jsonResponse.getJSONObject("data");
            RES = objectMapper.readValue(jsonData.toString(), DataQueryDTO.class);
            lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            log.info(Header.getMsgid() + " Response CheckBill: " + response);
        } catch (HttpClientErrorException e) {
            try {
                String noQuotes = StringEscapeUtils.unescapeJson(e.getResponseBodyAsString().replaceAll("^\"|\"$", ""));
                JSONObject jsonResponse = new JSONObject(noQuotes);
                JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                RES = objectMapper.readValue(jsonData.toString(), DataQueryDTO.class);
                lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            } catch (Exception ex) {
                log.info(REQ.getHeader().getMsgid() + " Exception CheckBill: " + ex);
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (ResourceAccessException e) {
            log.info(REQ.getHeader().getMsgid() + " ResourceAccessException CheckBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } catch (Exception e) {
            log.info(REQ.getHeader().getMsgid() + " Exception CheckBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel = ResponseModel.builder().resCode(lpbResCode).data(RES).build();
        log.info(Header.getMsgid() + " Response: " + responseModel);
        return responseModel;
    }
}
