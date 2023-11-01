package com.lpb.esb.service.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.response.*;
import com.lpb.esb.service.common.utils.RequestUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudv1 on 2022-02-24
 */
@Log4j2
public class ConnectInfoService {
    public static final String URL = "http://esb-config-service/permission/get-service-info?serviceId=%s&productCode=%s&hasRole=%s";
    public static final String URL_ERROR = "http://esb-config-service/partner/get-error-message?serviceId=%s&partnerId=%s&partnerCode=%s";
    public static final String URL_GET_PARTNER = "http://esb-config-service/api/v1/settle/account?service_id=%s&provider_id=%s";
    public static final String URL_WITHOUT_HAS_ROLE = "http://esb-config-service/permission/get-service-info?serviceId=%s&productCode=%s";
    public static final String URL_GET_PARTNER_EVN = "http://esb-config-service/api/v1/partner/account?service_id=%s&product_code=%s&provider_id=%s";

    public static final String URL_GET_MAP_BILL_PRINT_EVN = "http://esb-config-service/api/v1/evn/bill_print?product_code=%s&payment_method=%s&channel=%s&bill_print=%s";
    public static List<ServiceInfo> getServiceInfo(RestTemplate restTemplate, String serviceId, String productCode, String hasRole) {
        String api = String.format(URL
            , serviceId
            , productCode
            , hasRole
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        ResponseEntity<String> res = restTemplate.getForEntity(api, String.class);
        String body = res.getBody();
        List<ServiceInfo> list = new ArrayList<>();
        try {
            ResponseModel<List<ServiceInfo>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<List<ServiceInfo>>>() {
            });
            list = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return list;
    }

    public static List<ServiceInfo> getServiceInfo(RestTemplate restTemplate, String serviceId, String productCode) {
        String api = String.format(URL_WITHOUT_HAS_ROLE
            , serviceId
            , productCode
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        ResponseEntity<String> res = restTemplate.getForEntity(api, String.class);
        String body = res.getBody();
        List<ServiceInfo> list = new ArrayList<>();
        try {
            ResponseModel<List<ServiceInfo>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<List<ServiceInfo>>>() {
            });
            list = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return list;
    }

    public static List<SettleAccountInfo> getPartnerInfo(RestTemplate restTemplate, String serviceId, String... merchantIds) {
        String merchantId = String.join(",", merchantIds);
        String api = String.format(URL_GET_PARTNER
            , serviceId
            , merchantId
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        List<SettleAccountInfo> settleAccountInfo = null;
        try {
            ResponseEntity<String> res = RequestUtils.executeGetReq(restTemplate, api);
            String body = res.getBody();
            ResponseModel<List<SettleAccountInfo>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<List<SettleAccountInfo>>>() {
            });
            settleAccountInfo = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return settleAccountInfo;
    }

    public static EsbErrorPartnerMessage getErrorMessage(RestTemplate restTemplate, String serviceId, String partnerId, String partnerCode) {
        String api = String.format(URL_ERROR
            , serviceId
            , partnerId
            , partnerCode
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        ResponseEntity<String> res = restTemplate.getForEntity(api, String.class);
        String body = res.getBody();
        EsbErrorPartnerMessage errorPartnerMessage = new EsbErrorPartnerMessage();
        try {
            ResponseModel<EsbErrorPartnerMessage> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<EsbErrorPartnerMessage>>() {
            });
            errorPartnerMessage = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return errorPartnerMessage;
    }

    public static List<PartnerAccountInfo> getPartnerAccountInfo(RestTemplate restTemplate, String serviceId, String productCode, String providerId) {
        String api = String.format(URL_GET_PARTNER_EVN
            , serviceId
            , productCode
            , providerId
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        List<PartnerAccountInfo> partnerAccountInfo = null;
        try {
            ResponseEntity<String> res = RequestUtils.executeGetReq(restTemplate, api);
            String body = res.getBody();
            ResponseModel<List<PartnerAccountInfo>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<List<PartnerAccountInfo>>>() {
            });
            partnerAccountInfo = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return partnerAccountInfo;
    }

    public static String getBillPrintEvnInfo(RestTemplate restTemplate, String productCode, String paymentMethod, String channel, String billPrint) {
        String api = String.format(URL_GET_MAP_BILL_PRINT_EVN
            , productCode
            , paymentMethod
            , channel
            , billPrint
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        String mapEvn = null;
        try {
            ResponseEntity<String> res = RequestUtils.executeGetReq(restTemplate, api);
            String body = res.getBody();
            ResponseModel<String> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<String>>() {
            });
            mapEvn = responseModel.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
        }
        return mapEvn;
    }

}
