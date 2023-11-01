package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.common.ExceptionPurchase;
import com.lpb.napas.ecom.common.Response;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.MethodActionConfig;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.IInitPurchaseResponse;
import com.lpb.napas.ecom.process.IInitTransactionProcess;
import com.lpb.napas.ecom.process.IPurchaseProcess;
import com.lpb.napas.ecom.service.EsbCardCoreUserInfoService;
import com.lpb.napas.ecom.service.IEsbSystemEcomLogService;
import com.lpb.napas.ecom.service.IEsbUtilService;
import com.lpb.napas.ecom.utils.AESUtils;
import com.lpb.napas.ecom.utils.RSAUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class PurchaseProcessImpl implements IPurchaseProcess {
    @Autowired
    IInitPurchaseResponse initPurchaseResponse;

    @Autowired
    IEsbSystemEcomLogService esbSystemEcomLogService;

    @Autowired
    MethodActionConfig methodActionConfig;

    @Autowired
    IInitTransactionProcess initTransactionProcess;

    @Autowired
    IEsbUtilService esbUtilService;

    @Autowired
    EsbCardCoreUserInfoService esbCardCoreUserInfoService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public PurchaseResponse initPurchaseResponse(PurchaseRequest purchaseRequest) throws JsonProcessingException {
        InitPurchaseRequest initPurchaseRequest = new InitPurchaseRequest();

        EsbSystemEcomLog esbSystemEcomLogNew = new EsbSystemEcomLog();
        esbSystemEcomLogNew.setMsgId(esbSystemEcomLogService.getAppMsgID());
        esbSystemEcomLogNew.setRequestorCode(purchaseRequest.getRequestorCode());
        esbSystemEcomLogNew.setRequestorTransId(purchaseRequest.getRequestorTransId());
        esbSystemEcomLogNew.setMethodAction(methodActionConfig.getPurchase());
        initPurchaseRequest.setPartnerTransId(esbSystemEcomLogNew.getMsgId());

        String inputSignatureData = String.format("%s%s%s%s%s",
            purchaseRequest.getRequestorCode(), purchaseRequest.getRequestorPassword(),
            purchaseRequest.getRequestorTransId(), purchaseRequest.getData(), serviceConfig.getSecretKey());
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(purchaseRequest.getRequestorTransId() + " Input: " + objectMapper.writeValueAsString(purchaseRequest));
        log.info(purchaseRequest.getRequestorTransId() + " InputSignatureData: " + inputSignatureData);
        String inputData = AESUtils.decryptWithAES(purchaseRequest.getData());
        log.info(purchaseRequest.getRequestorTransId() + " InputData: " + inputData);

        DataPurchaseRequest dataPurchaseRequest = objectMapper.readValue(inputData, DataPurchaseRequest.class);
        try {
            // chỉ chạy live môi trường test không có
            if (!purchaseRequest.getRequestorCode().equals(Constant.REQUESTOR_CODE)
                || !purchaseRequest.getRequestorPassword().equals(Constant.REQUESTOR_PW)) {

                initPurchaseRequest.setResponseCode(Response.WRONG_PARTER_PASSWORD.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.WRONG_PARTER_PASSWORD.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, null);
            }
            // validate đầu vào
            if (Objects.isNull(purchaseRequest.getRequestorCode())
                || Objects.isNull(purchaseRequest.getRequestorPassword())
                || Objects.isNull(purchaseRequest.getRequestorTransId())
                || Objects.isNull(purchaseRequest.getSignature())) {

                esbSystemEcomLogNew.setErrorCode(Response.INVALIID.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALIID.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.INVALIID.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.INVALIID.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, null);
            }
            //kiểm tra chữ kí
            EsbCardCoreUserInfo esbCardCoreUserInfo = this.esbCardCoreUserInfoService.
                getEsbCardCoreUserInfoByUsername(Constant.ESB_CARD_CORE_USER_INFO_NAPAS_ECOM);
            Boolean verifySignature = RSAUtils.verifySignature(RSAUtils
                    .getPublicKeyFromPemFile(esbCardCoreUserInfo.getClientPublicKey()), inputSignatureData,
                purchaseRequest.getSignature(), Constant.SHA256withRSA);
            if (!verifySignature) {
                esbSystemEcomLogNew.setErrorCode(Response.WRONG_SIGNATURE.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.WRONG_SIGNATURE.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.WRONG_SIGNATURE.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.WRONG_SIGNATURE.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, null);
            }
            // kiểm tra xem giao dịch này đã qua api verify otp chưa
            List<EsbSystemEcomLog> listEsbSystemEcomLog = esbSystemEcomLogService.getListEsbSystemEcomLog
                (purchaseRequest.getRequestorTransId(), methodActionConfig.getVerifyOTP());
            if (listEsbSystemEcomLog.isEmpty()) {

                esbSystemEcomLogNew.setErrorCode(Response.INVALID_TRANSACTION.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALID_TRANSACTION.getResponseMessage());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.INVALID_TRANSACTION.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.INVALID_TRANSACTION.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, null);
            }
            // kiểm tra status của api verify otp
            EsbSystemEcomLog esbSystemEcomLog = listEsbSystemEcomLog.get(0);
            if (!esbSystemEcomLog.getErrorCode().equals("0")) {

                esbSystemEcomLogNew.setErrorCode(Response.INVALID_TRANSACTION_STATUS.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.INVALID_TRANSACTION_STATUS.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.INVALID_TRANSACTION_STATUS.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.INVALID_TRANSACTION_STATUS.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
            }
            // kiểm tra xem tồn tại RequestorTransId
            EsbSystemEcomLog checkExitsTransaction = esbSystemEcomLogService.checkExitsTransaction
                (purchaseRequest.getRequestorTransId(), methodActionConfig.getPurchase());
            if (checkExitsTransaction != null) {

                esbSystemEcomLogNew.setErrorCode(Response.DUPLICATE_TRANSACTION.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.DUPLICATE_TRANSACTION.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.DUPLICATE_TRANSACTION.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.DUPLICATE_TRANSACTION.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
            }
            // kiểm tra transaction bằng việc gọi transaction với appId dùng chung vs 3 api
            ResponseModel<List<TransactionPostInfoDTO>> listTransactionInitDTO =
                initTransactionProcess.executeGetInitTransactionRequest(esbSystemEcomLog);
            if (listTransactionInitDTO.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLogNew.setErrorCode(Response.TRANSACTION_DOES_NOT_EXIST.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.TRANSACTION_DOES_NOT_EXIST.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.TRANSACTION_DOES_NOT_EXIST.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.TRANSACTION_DOES_NOT_EXIST.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
            }
            //gọi hạch toán
            UploadTransferJRNDTO uploadTransferJRNDTO = initTransactionProcess.
                initUploadTransferJRNDTO(listTransactionInitDTO.getData(), esbSystemEcomLog, purchaseRequest);
            ResponseModel<String> executeTransactionUploadTransferJrnRequest =
                initTransactionProcess.executeUploadTransferJrnRequest(uploadTransferJRNDTO);
            if (executeTransactionUploadTransferJrnRequest.getResCode().getErrorCode().equals(ErrorMessage.AVAILABLE_AMOUNT.label)) {

                esbSystemEcomLogNew.setErrorCode(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());

            }
            if (!executeTransactionUploadTransferJrnRequest.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {

                esbSystemEcomLogNew.setErrorCode(Response.NOT_ALLOWED_TRANSACTION.getResponseCode());
                esbSystemEcomLogNew.setErrorDesc(Response.NOT_ALLOWED_TRANSACTION.getResponseMessage());
                esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
                esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLogNew);

                initPurchaseRequest.setResponseCode(Response.NOT_ALLOWED_TRANSACTION.getResponseCode());
                initPurchaseRequest.setResponseMessage(Response.NOT_ALLOWED_TRANSACTION.getResponseMessage());

                throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());

            }
            esbSystemEcomLogNew.setErrorCode(Response.SUCCESSFULL.getResponseCode());
            esbSystemEcomLogNew.setErrorDesc(Response.SUCCESSFULL.getResponseMessage());
            esbSystemEcomLogNew.setAppId(esbSystemEcomLog.getAppId());
            esbSystemEcomLogNew.setStatus(ErrorMessage.SUCCESS.label);
            esbSystemEcomLogService.save(esbSystemEcomLogNew);

            initPurchaseRequest.setResponseCode(Response.SUCCESSFULL.getResponseCode());
            initPurchaseRequest.setResponseMessage(Response.SUCCESSFULL.getResponseMessage());

            return initPurchaseResponse.initPurchaseResponse(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
        } catch (ExceptionPurchase e) {
            throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
        } catch (Exception e) {
            log.error(purchaseRequest.getRequestorTransId() + "-" + esbSystemEcomLogNew.getAppId() + " Exception Purchase: " + e);
            initPurchaseRequest.setResponseCode(Response.INTERNAL_ERROR.getResponseCode());
            initPurchaseRequest.setResponseMessage(Response.INTERNAL_ERROR.getResponseMessage());

            throw new ExceptionPurchase(purchaseRequest, initPurchaseRequest, dataPurchaseRequest, esbSystemEcomLogNew.getAppId());
        } finally {
            initPurchaseResponse.updateTransaction(initPurchaseRequest, esbSystemEcomLogNew.getAppId());
        }
    }
}
