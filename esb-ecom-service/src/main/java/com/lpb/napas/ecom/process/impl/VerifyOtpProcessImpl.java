package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.common.ExceptionVerifyOtp;
import com.lpb.napas.ecom.common.Response;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.MethodActionConfig;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.IInitVerifyOtpResponse;
import com.lpb.napas.ecom.process.IVerifyOtpProcess;
import com.lpb.napas.ecom.service.EsbCardCoreUserInfoService;
import com.lpb.napas.ecom.service.IEsbSystemEcomLogService;
import com.lpb.napas.ecom.service.IEsbUtilService;
import com.lpb.napas.ecom.utils.AESUtils;
import com.lpb.napas.ecom.utils.RSAUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class VerifyOtpProcessImpl implements IVerifyOtpProcess {
    @Autowired
    MethodActionConfig methodActionConfig;

    @Autowired
    IEsbSystemEcomLogService esbSystemEcomLogService;

    @Autowired
    IInitVerifyOtpResponse initVerifyOtpResponse;

    @Autowired
    IVerifyOtpProcess verifyOtpProcess;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    IEsbUtilService esbUtilService;

    @Autowired
    EsbCardCoreUserInfoService esbCardCoreUserInfoService;

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseModel executeVerifyOtpRequest(VerifyOtpREQDTO verifyOtpREQDTO, String requestorTransId, String appId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ResponseModel dataResponseModel = ResponseModel.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<VerifyOtpREQDTO> entity = new HttpEntity<>(verifyOtpREQDTO, headers);
            String responseVerifyOtp = restTemplate.exchange
                (serviceApiConfig.getOtpVerifyOtp(), HttpMethod.POST, entity, String.class).getBody();
            dataResponseModel = objectMapper.readValue(responseVerifyOtp, ResponseModel.class);

            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            }
            log.info(requestorTransId + "-" + appId + " Get CardInfo: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            try {
                dataResponseModel = objectMapper.readValue(e.getResponseBodyAsString(), ResponseModel.class);
                if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.WRONG_OTP.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.WRONG_OTP.label);
                    lpbResCode.setErrorDesc(ErrorMessage.WRONG_OTP.description);
                } else if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.EXPIRED_OTP.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.EXPIRED_OTP.label);
                    lpbResCode.setErrorDesc(ErrorMessage.EXPIRED_OTP.description);
                } else if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.WRONG_MANY_OTP.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.WRONG_MANY_OTP.label);
                    lpbResCode.setErrorDesc(ErrorMessage.WRONG_MANY_OTP.description);
                } else {
                    lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                    lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
                }
            } catch (Exception ex) {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.error(requestorTransId + "-" + appId + " VerifyOtp: " + lpbResCode.getErrorDesc());
        } catch (Exception e) {
            log.error("Exception VerifyOtp" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);

        return responseModel;
    }

    @Override
    public com.lpb.napas.ecom.dto.VerifyOtpResponse verifyOtpProcess(VerifyOtpRequest verifyOtpRequest) throws JsonProcessingException {
        InitVerifyOtpRequest initVerifyOtpRequest = new InitVerifyOtpRequest();

        EsbSystemEcomLog esbSystemEcomLogNew = new EsbSystemEcomLog();
        esbSystemEcomLogNew.setRequestorCode(verifyOtpRequest.getRequestorCode());
        esbSystemEcomLogNew.setRequestorTransId(verifyOtpRequest.getRequestorTransId());
        esbSystemEcomLogNew.setMethodAction(methodActionConfig.getVerifyOTP());

        String inputSignatureData = String.format("%s%s%s%s%s",
            verifyOtpRequest.getRequestorCode(), verifyOtpRequest.getRequestorPassword(),
            verifyOtpRequest.getRequestorTransId(), verifyOtpRequest.getData(), serviceConfig.getSecretKey());
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(verifyOtpRequest.getRequestorTransId() + " Input: " + objectMapper.writeValueAsString(verifyOtpRequest));
        log.info(verifyOtpRequest.getRequestorTransId() + " InputSignatureData: " + inputSignatureData);
        String inputData = AESUtils.decryptWithAES(verifyOtpRequest.getData());
        log.info(verifyOtpRequest.getRequestorTransId() + " InputData: " + inputData);

        DataVerifyOtpRequest dataVerifyOtpRequest = objectMapper.readValue(inputData, DataVerifyOtpRequest.class);
        try {
            // chỉ chạy live môi trường test không có
            if (!verifyOtpRequest.getRequestorCode().equals(Constant.REQUESTOR_CODE)
                || !verifyOtpRequest.getRequestorPassword().equals(Constant.REQUESTOR_PW)) {

                initVerifyOtpRequest.setResponseCode(Response.WRONG_PARTER_PASSWORD.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.WRONG_PARTER_PASSWORD.getResponseMessage());
                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, null);
            }
            EsbSystemEcomLog checkExitsTransaction = esbSystemEcomLogService.
                checkExitsTransaction(verifyOtpRequest.getRequestorTransId(), methodActionConfig.getVerifyOTP());
            // kiểm tra xem tồn tại của RequestorTransId
            if (checkExitsTransaction != null && checkExitsTransaction.getLastEventSeqNo() >= 3) {
                initVerifyOtpRequest.setResponseCode(Response.DUPLICATE_TRANSACTION.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.DUPLICATE_TRANSACTION.getResponseMessage());

                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, checkExitsTransaction.getAppId());
            }
            // tồn tại thì update
            if (checkExitsTransaction != null && checkExitsTransaction.getMsgId() != null) {
                esbSystemEcomLogNew.setAppId(checkExitsTransaction.getAppId());
                esbSystemEcomLogNew.setMsgId(checkExitsTransaction.getMsgId());
                esbSystemEcomLogNew.setLastEventSeqNo(checkExitsTransaction.getLastEventSeqNo() + 1);
                esbSystemEcomLogNew.setCreateDate(checkExitsTransaction.getCreateDate());
                esbSystemEcomLogNew.setUpdatedDate(new Date());
                initVerifyOtpRequest.setPartnerTransId(esbSystemEcomLogNew.getMsgId());
            } else {
                String appMsgId = esbSystemEcomLogService.getAppMsgID();
                esbSystemEcomLogNew.setMsgId(appMsgId);
                esbSystemEcomLogNew.setLastEventSeqNo(1);
                initVerifyOtpRequest.setPartnerTransId(esbSystemEcomLogNew.getMsgId());
            }
            //validate đầu vào
            if (Objects.isNull(verifyOtpRequest.getRequestorCode())
                || Objects.isNull(verifyOtpRequest.getRequestorPassword())
                || Objects.isNull(verifyOtpRequest.getRequestorTransId())
                || Objects.isNull(verifyOtpRequest.getSignature())
                || Objects.isNull(dataVerifyOtpRequest.getTransaction().getOtp())) {

                esbSystemEcomLogNew.setErrorCode(Response.INVALIID.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALIID.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initVerifyOtpRequest.setResponseCode(Response.INVALIID.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.INVALIID.getResponseMessage());

                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
            }
            //kiểm tra chữ kí
            EsbCardCoreUserInfo esbCardCoreUserInfo = this.esbCardCoreUserInfoService.
                getEsbCardCoreUserInfoByUsername(Constant.ESB_CARD_CORE_USER_INFO_NAPAS_ECOM);
            Boolean verifySignature = RSAUtils.verifySignature(RSAUtils
                    .getPublicKeyFromPemFile(esbCardCoreUserInfo.getClientPublicKey()), inputSignatureData,
                verifyOtpRequest.getSignature(), Constant.SHA256withRSA);
            if (!verifySignature) {
                esbSystemEcomLogNew.setErrorCode(Response.WRONG_SIGNATURE.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.WRONG_SIGNATURE.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initVerifyOtpRequest.setResponseCode(Response.WRONG_SIGNATURE.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.WRONG_SIGNATURE.getResponseMessage());

                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
            }
            List<EsbSystemEcomLog> listEsbSystemEcomLog = esbSystemEcomLogService.
                getListEsbSystemEcomLog(verifyOtpRequest.getRequestorTransId(), methodActionConfig.getVerifyPayment());
            //kiểm tra xem giao dịch này đã được verify payment chưa
            if (listEsbSystemEcomLog.isEmpty()) {
                esbSystemEcomLogNew.setErrorCode(Response.INVALID_TRANSACTION.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALID_TRANSACTION.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initVerifyOtpRequest.setResponseCode(Response.INVALID_TRANSACTION.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.INVALID_TRANSACTION.getResponseMessage());

                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
            }

            EsbSystemEcomLog esbSystemEcomLog = listEsbSystemEcomLog.get(0);
            //kiểm tra xem giao dịch này đã được trả vể status có phải 0
            if (!esbSystemEcomLog.getErrorCode().equals("0")) {
                esbSystemEcomLogNew.setErrorCode(Response.INVALID_TRANSACTION_STATUS.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALID_TRANSACTION_STATUS.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initVerifyOtpRequest.setResponseCode(Response.INVALID_TRANSACTION_STATUS.getResponseCode());
                initVerifyOtpRequest.setResponseMessage(Response.INVALID_TRANSACTION_STATUS.getResponseMessage());

                throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                    dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
            }

            VerifyOtpREQDTO verifyOtpREQDTO = VerifyOtpREQDTO.builder()
                .appMsgId(esbSystemEcomLog.getAppId())
                .userId(serviceConfig.getUserNapas())
                .otpCode(dataVerifyOtpRequest.getTransaction().getOtp())
                .build();

            //kiểm tra send otp với appid dùng chung còn msg mỗi api là 1 cái riêng việt
            ResponseModel verifyOtpResponse = verifyOtpProcess.executeVerifyOtpRequest
                (verifyOtpREQDTO, verifyOtpRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (!verifyOtpResponse.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                if (verifyOtpResponse.getResCode().getErrorCode().equals(ErrorMessage.EXPIRED_OTP.label)
                    || verifyOtpResponse.getResCode().getErrorCode().equals(ErrorMessage.WRONG_MANY_OTP.label)) {

                    esbSystemEcomLogNew.setErrorCode(Response.EXPIRED_OTP.getResponseCode());
                    esbSystemEcomLogNew.setErrorDesc(Response.EXPIRED_OTP.getResponseMessage());
                    esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                    esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                    esbSystemEcomLogService.save(esbSystemEcomLogNew);

                    initVerifyOtpRequest.setResponseCode(Response.EXPIRED_OTP.getResponseCode());
                    initVerifyOtpRequest.setResponseMessage(Response.EXPIRED_OTP.getResponseMessage());

                    throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                        dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
                } else {

                    esbSystemEcomLogNew.setErrorCode(Response.WRONG_OTP.getResponseCode());
                    esbSystemEcomLogNew.setErrorDesc(Response.WRONG_OTP.getResponseMessage());
                    esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                    esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                    esbSystemEcomLogService.save(esbSystemEcomLogNew);

                    initVerifyOtpRequest.setResponseCode(Response.WRONG_OTP.getResponseCode());
                    initVerifyOtpRequest.setResponseMessage(Response.WRONG_OTP.getResponseMessage());

                    throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                        dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
                }
            }

            initVerifyOtpRequest.setResponseCode(Response.SUCCESSFULL.getResponseCode());
            initVerifyOtpRequest.setResponseMessage(Response.SUCCESSFULL.getResponseMessage());

            esbSystemEcomLogNew.setErrorCode(Response.SUCCESSFULL.getResponseCode());
            esbSystemEcomLogNew.setErrorDesc(Response.SUCCESSFULL.getResponseMessage());
            esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
            esbSystemEcomLogNew.setStatus(ErrorMessage.SUCCESS.label);
            esbSystemEcomLogService.save(esbSystemEcomLogNew);

            return initVerifyOtpResponse.initVerifyOtpResponse(verifyOtpRequest, initVerifyOtpRequest,
                dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
        } catch (ExceptionVerifyOtp e) {
            throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest, dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
        } catch (Exception e) {
            log.error(verifyOtpRequest.getRequestorTransId() + "-" + esbSystemEcomLogNew.getAppId() + " Exception VerifyOtp: " + e);
            initVerifyOtpRequest.setResponseCode(Response.INTERNAL_ERROR.getResponseCode());
            initVerifyOtpRequest.setResponseMessage(Response.INTERNAL_ERROR.getResponseMessage());

            throw new ExceptionVerifyOtp(verifyOtpRequest, initVerifyOtpRequest,
                dataVerifyOtpRequest, esbSystemEcomLogNew.getAppId());
        }
    }
}
