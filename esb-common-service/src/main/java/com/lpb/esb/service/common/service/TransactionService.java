package com.lpb.esb.service.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class TransactionService {
    public static final String INIT_TRANSACTION = "http://localhost:18601/api/v1/transaction/init-transaction";
    public static final String GET_TRANSACTION = "http://esb-config-service/api/v1/transaction/get-transaction-post";
    public static final String UPDATE_TRANSACTION = "http://esb-config-service/api/v1/transaction/update-transaction";
    public static final String INIT_SETTLE_BILL = "http://esb-config-service/api/v1/settle-bill/init-settle-bill";
    public static final String GET_SETTLE_BILL = "http://esb-config-service/api/v1/settle-bill/get-settle-bill";
    public static final String UPDATE_SETTLE_BILL = "http://esb-config-service/api/v1/settle-bill/update-settle-bill";

    public static final String BILLING_LOG = "http://esb-config-service/api/v1/settle-bill/billing_log";

    public static final String INIT_REVERT_BILL = "http://esb-config-service/api/v1/revert-bill/init-revert-bill";
    public static final String GET_REVERT_BILL = "http://esb-config-service/api/v1/revert-bill/get-revert-bill";

    public static ResponseModel initTransaction(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, INIT_TRANSACTION,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel getTransaction(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, GET_TRANSACTION,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel updateTransaction(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, UPDATE_TRANSACTION,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel initSettleBill(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, INIT_SETTLE_BILL,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel getSettleBill(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, GET_SETTLE_BILL,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel updateSettleBill(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, UPDATE_SETTLE_BILL,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel billingLog(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, BILLING_LOG,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel initRevertBill(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, INIT_REVERT_BILL,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    public static ResponseModel getRevertBill(RestTemplate restTemplate, BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, GET_REVERT_BILL,
                objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = objectMapper.readValue(res.getBody(), ResponseModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage(), e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }
}
