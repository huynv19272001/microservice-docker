package com.lpb.esb.service.pti.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.pti.config.RestTemplateConfig;
import com.lpb.esb.service.pti.config.ServiceConfig;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.model.pti.PtiRequest;
import com.lpb.esb.service.pti.model.pti.PtiResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class PtiUtils {
    @Autowired
    RestTemplateConfig restTemplate;
    @Autowired
    ServiceConfig serviceConfig;

    private LpbResCode lpbResCode;

    public HttpHeaders getHeaders(List<ServiceInfo> serviceInfos) {
        ServiceInfo serviceInfo = serviceInfos.get(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("UserName", serviceInfo.getUdf1());
        headers.set("PassWord", serviceInfo.getUdf2());
        headers.set("SecretKey", serviceInfo.getUdf3());
        headers.set("Channel", serviceInfo.getUdf4());
        headers.set("BlockCode", " ");
        headers.set("BranchUnit", serviceInfo.getUdf6());
        return headers;
    }

    public void checkPtiResCode(PtiResponse ptiResponse) {
        lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        if (!ptiResponse.getCode().equals("000")) {
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service Failure")
                .refCode(ptiResponse.getCode())
                .refDesc(ptiResponse.getMessage())
                .build();
        }
    }

    @SneakyThrows
    public ResponseModel sendPtiRequest(EsbRequestDTO data, PtiRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel;

        log.info("msgId: " + data.getHeader().getMsgId() + " | PTI Req: " + objectMapper.writeValueAsString(request));

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

        if (serviceInfos == null || serviceInfos.size() <= 0) {
            lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
            responseModel = ResponseModel.builder().resCode(lpbResCode).build();
            return responseModel;
        }

        HttpHeaders headers = getHeaders(serviceInfos);
        HttpEntity<PtiRequest> entity = new HttpEntity<>(request, headers);

        String response = restTemplate.getRestTemplate().exchange(serviceInfos.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

        log.info("msgId: " + data.getHeader().getMsgId() + " | PTI Res: " + response);
        PtiResponse ptiResponse = objectMapper.readValue(response, PtiResponse.class);

        checkPtiResCode(ptiResponse);

        responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(ptiResponse.getData())
            .build();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Res: " + objectMapper.writeValueAsString(responseModel));
        return responseModel;
    }
}
