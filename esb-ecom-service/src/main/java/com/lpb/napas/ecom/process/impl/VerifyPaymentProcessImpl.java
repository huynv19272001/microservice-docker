package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.common.ExceptionVerifyPayment;
import com.lpb.napas.ecom.common.Response;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.model.EsbLimitAmt;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.MethodActionConfig;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.*;
import com.lpb.napas.ecom.service.*;
import com.lpb.napas.ecom.utils.AESUtils;
import com.lpb.napas.ecom.utils.DateUtils;
import com.lpb.napas.ecom.utils.RSAUtils;
import com.lpb.napas.ecom.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class VerifyPaymentProcessImpl implements IVerifyPaymentProcess {
    @Autowired
    IInitVerifyPaymentResponse initVerifyPaymentResponse;

    @Autowired
    ICreateOtpProcess createOtpProcess;

    @Autowired
    IInitTransactionProcess initTransactionProcess;

    @Autowired
    ICardProcess cardInfoProcess;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    IEsbSystemEcomLogService esbSystemEcomLogService;

    @Autowired
    IEsbUtilService esbUtilService;

    @Autowired
    MethodActionConfig methodActionConfig;

    @Autowired
    EsbCardCoreUserInfoService esbCardCoreUserInfoService;

    @Autowired
    ISMSService iSMSService;

    @Autowired
    IGetAvlBalanceProcess iGetAvlBalanceProcess;

    @Autowired
    EsbLimitAmtService esbLimitAmtService;

    @Autowired
    ValidAmtService validAmtService;

    @Override
    public VerifyPaymentResponse initVerifyPaymentProcess(VerifyPaymentRequest verifyPaymentRequest) throws JsonProcessingException {
        InitVerifyPaymentRequest initVerifyPaymentRequest = new InitVerifyPaymentRequest();

        EsbSystemEcomLog esbSystemEcomLog = new EsbSystemEcomLog();
        esbSystemEcomLog.setRequestorCode(verifyPaymentRequest.getRequestorCode());
        esbSystemEcomLog.setRequestorTransId(verifyPaymentRequest.getRequestorTransId());
        esbSystemEcomLog.setMethodAction(methodActionConfig.getVerifyPayment());

        String inputSignatureData = String.format("%s%s%s%s%s",
            verifyPaymentRequest.getRequestorCode(), verifyPaymentRequest.getRequestorPassword(),
            verifyPaymentRequest.getRequestorTransId(), verifyPaymentRequest.getData(), serviceConfig.getSecretKey());
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(verifyPaymentRequest.getRequestorTransId() + " Input: " + objectMapper.writeValueAsString(verifyPaymentRequest));
        log.info(verifyPaymentRequest.getRequestorTransId() + " InputSignatureData: " + inputSignatureData);
        String inputData = AESUtils.decryptWithAES(verifyPaymentRequest.getData());
        log.info(verifyPaymentRequest.getRequestorTransId() + " InputData: " + inputData);

        DataVerifyPaymentRequest dataVerifyPaymentRequest = objectMapper.readValue(inputData, DataVerifyPaymentRequest.class);
        try {
            // chỉ chạy live môi trường test không có
            if (!verifyPaymentRequest.getRequestorCode().equals(Constant.REQUESTOR_CODE)
                || !verifyPaymentRequest.getRequestorPassword().equals(Constant.REQUESTOR_PW)) {

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_PARTER_PASSWORD.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_PARTER_PASSWORD.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, null);
            }
            EsbSystemEcomLog checkExitsTransaction = esbSystemEcomLogService.
                checkExitsTransaction(verifyPaymentRequest.getRequestorTransId(), methodActionConfig.getVerifyPayment());
            // kiểm tra xem tồn tại của RequestorTransId
            if (checkExitsTransaction != null && checkExitsTransaction.getLastEventSeqNo() >= 3) {
                initVerifyPaymentRequest.setResponseCode(Response.DUPLICATE_TRANSACTION.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.DUPLICATE_TRANSACTION.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            // tồn tại thì update
            if (checkExitsTransaction != null && checkExitsTransaction.getMsgId() != null) {
                esbSystemEcomLog.setAppId(checkExitsTransaction.getAppId());
                esbSystemEcomLog.setMsgId(checkExitsTransaction.getMsgId());
                esbSystemEcomLog.setLastEventSeqNo(checkExitsTransaction.getLastEventSeqNo() + 1);
                esbSystemEcomLog.setCreateDate(checkExitsTransaction.getCreateDate());
                esbSystemEcomLog.setUpdatedDate(new Date());
                initVerifyPaymentRequest.setPartnerTransId(checkExitsTransaction.getMsgId());
            } else {
                String appMsgId = esbSystemEcomLogService.getAppMsgID();
                esbSystemEcomLog.setAppId(appMsgId);
                esbSystemEcomLog.setMsgId(appMsgId);
                esbSystemEcomLog.setLastEventSeqNo(1);
                initVerifyPaymentRequest.setPartnerTransId(esbSystemEcomLog.getMsgId());
            }

            if (!validateInput(verifyPaymentRequest, dataVerifyPaymentRequest)) {

                esbSystemEcomLog.setErrorCode(Response.INVALIID.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.INVALIID.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.INVALIID.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.INVALIID.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //kiểm tra chữ kí
            EsbCardCoreUserInfo esbCardCoreUserInfo = this.esbCardCoreUserInfoService.
                getEsbCardCoreUserInfoByUsername(Constant.ESB_CARD_CORE_USER_INFO_NAPAS_ECOM);
            Boolean verifySignature = RSAUtils.verifySignature(RSAUtils
                    .getPublicKeyFromPemFile(esbCardCoreUserInfo.getClientPublicKey()), inputSignatureData,
                verifyPaymentRequest.getSignature(), Constant.SHA256withRSA);
            if (!verifySignature) {
                esbSystemEcomLog.setErrorCode(Response.WRONG_SIGNATURE.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.WRONG_SIGNATURE.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_SIGNATURE.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_SIGNATURE.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            CardInfoREQDTO cardInfoRequest = new CardInfoREQDTO();
            cardInfoRequest.setCardNumber(dataVerifyPaymentRequest.getCard().getNumber());
            cardInfoRequest.setInputType(dataVerifyPaymentRequest.getCard().getType());
            cardInfoRequest.setAppId(esbSystemEcomLog.getAppId());
            ResponseModel<List<CardInfoDTO>> listCardInfoResponse = cardInfoProcess.
                executeListCardInfo(cardInfoRequest, verifyPaymentRequest.getRequestorTransId());

            if (listCardInfoResponse.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.WRONG_CARD_INFO.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.WRONG_CARD_INFO.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_CARD_INFO.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_CARD_INFO.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            CardInfoDTO cardInfo = listCardInfoResponse.getData().get(0);
            //Kiểm tra type card:
            // chỉ lấy thẻ LOCAL_DEBIT với 6 số đầu là 970449
            String typeCard = cardInfo.getCardNumber().substring(0, 6);
            if (!typeCard.equals(Constant.DEBIT_LOCAL)) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //thêm trường hợp thẻ bị khóa check status và cái tổng kia thành CARD_ACCOUNT_IS_INVALID

            //kiểm tra Thẻ hết hạn sai
            if (cardInfo.getCardStatus().equals(Constant.CARD_STATUS_CSTS22)) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //kiểm tra Tên trên thẻ / tên tài khoản sai
            if (!cardInfo.getEmbossedName().equals(dataVerifyPaymentRequest.getCard().getNameOnCard())) {

                esbSystemEcomLog.setErrorCode(Response.WRONG_NAME_ON_CARD_ACCOUNT_NAME.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.WRONG_NAME_ON_CARD_ACCOUNT_NAME.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_NAME_ON_CARD_ACCOUNT_NAME.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_NAME_ON_CARD_ACCOUNT_NAME.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            //kiểm tra Thẻ bị khóa
            //kiểm tra tổng trạng thái của thẻ
            if (!cardInfo.getCardStatus().equals(Constant.CARD_STATUS_CSTS0)) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //Trạng thái chặn giao dịch ECOM:
            if (cardInfo.getEcom().equals("1")) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(
                    Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(
                    Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //kiểm tra số điện thoại
            if (StringUtil.isEmptyOrNull(cardInfo.getPhoneEpin())) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(
                    Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(
                    Response.CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            //kiểm tra ngày hết hạn
            Date issueDate = DateUtils.convertyyyyMMddHHssMM(cardInfo.getExpirationDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(issueDate);
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
            }
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            if (!dataVerifyPaymentRequest.getCard().getYear().equals(year) ||
                !dataVerifyPaymentRequest.getCard().getMonth().equals(month)) {

                esbSystemEcomLog.setErrorCode(Response.WRONG_CARD_EXPIRY.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.WRONG_CARD_EXPIRY.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_CARD_EXPIRY.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_CARD_EXPIRY.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            //tạo init get GetAvlBalanceDTO:
            GetAvlBalanceREQDTO avlBalanceREQDTO = iGetAvlBalanceProcess.initGetAvlBalanceDTO(cardInfo, esbSystemEcomLog, verifyPaymentRequest);
            //get GetAvlBalanceDTO:
            ResponseModel<List<GetAvlBalanceRESDTO>> accountList = iGetAvlBalanceProcess.excuteGetAvlBalance
                (avlBalanceREQDTO, verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (accountList.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.CARD_ACCOUNT_IS_INVALID.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.CARD_ACCOUNT_IS_INVALID.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            List<EsbLimitAmt> esbLimitAmt = esbLimitAmtService.getListByServiceId(serviceConfig.getServiceIdAtm());
            InitValidAmtRequest initValidAmtRequest = validAmtService.
                initValidAmtRequest(cardInfo, dataVerifyPaymentRequest, esbLimitAmt.get(0));

            //check số lần giao dịch trên 1 ngày
            ResponseModel executeModelValidCountAmtOneDay = validAmtService.
                validCountAmtOneDay(initValidAmtRequest, verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (executeModelValidCountAmtOneDay.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.TRANSACTION_NUMBER_EXCEEDS_LIMIT_PER_DAY.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.TRANSACTION_NUMBER_EXCEEDS_LIMIT_PER_DAY.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(
                    Response.TRANSACTION_NUMBER_EXCEEDS_LIMIT_PER_DAY.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(
                    Response.TRANSACTION_NUMBER_EXCEEDS_LIMIT_PER_DAY.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //check tổng hạn mức giao dịch trên 1 ngày
            ResponseModel executeModelValidAmtOneDay = validAmtService
                .validAmtOneDay(initValidAmtRequest, verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (executeModelValidAmtOneDay.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.TRANSACTION_AMOUNT_EXCEEDS_LIMIT_PER_DAY.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.TRANSACTION_AMOUNT_EXCEEDS_LIMIT_PER_DAY.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(
                    Response.TRANSACTION_AMOUNT_EXCEEDS_LIMIT_PER_DAY.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(
                    Response.TRANSACTION_AMOUNT_EXCEEDS_LIMIT_PER_DAY.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }
            //Hạn mức giao dịch ECOM trên 1 giao dịch
            ResponseModel executeModelValidAmtOneTxn = validAmtService
                .validAmtOneTxn(initValidAmtRequest, verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (executeModelValidAmtOneTxn.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.TRANSACTION_AMOUNT_EXCEEDS_ONE_TRANSACTION_LIMIT.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.TRANSACTION_AMOUNT_EXCEEDS_ONE_TRANSACTION_LIMIT.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(
                    Response.TRANSACTION_AMOUNT_EXCEEDS_ONE_TRANSACTION_LIMIT.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(
                    Response.TRANSACTION_AMOUNT_EXCEEDS_ONE_TRANSACTION_LIMIT.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            GetAvlBalanceRESDTO getAvlBalanceRES = accountList.getData().get(0);
            if (iGetAvlBalanceProcess.checkAvlBalance(getAvlBalanceRES, dataVerifyPaymentRequest) == false) {
                esbSystemEcomLog.setErrorCode(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            TransactionDTO transactionDTO = initTransactionProcess.initTransactionDTO
                (esbSystemEcomLog, verifyPaymentRequest, cardInfo,
                    dataVerifyPaymentRequest, getAvlBalanceRES);
            ResponseModel executeModelInitTransaction = initTransactionProcess.executeInitTransactionRequest(transactionDTO);
            if (executeModelInitTransaction.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.INVALID_TRANSACTION.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.INVALID_TRANSACTION.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.INVALID_TRANSACTION.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.INVALID_TRANSACTION.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            CreateOtpREQDTO createOtpRequest = new CreateOtpREQDTO();
            createOtpRequest.setMobileNo(listCardInfoResponse.getData().get(0).getPhoneEpin());
            //default config ESB_GATEWAY cần xem lại
            createOtpRequest.setUserId(serviceConfig.getUserNapas());
            createOtpRequest.setAppMsgId(esbSystemEcomLog.getAppId());

            ResponseModel<CreateOtpRESDTO> createOtpResponse = createOtpProcess.executeCreateOtpRequest
                (createOtpRequest, verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (createOtpResponse.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {

                esbSystemEcomLog.setErrorCode(Response.WRONG_OTP.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.WRONG_OTP.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.FAIL.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.WRONG_OTP.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.WRONG_OTP.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }

            //send sms
            String buildContent = String.format(serviceConfig.getContendSms(), createOtpResponse.getData().getOtp(),
                dataVerifyPaymentRequest.getTransaction().getAmount(), dataVerifyPaymentRequest.getMerchant().getCode());

            ResponseModel sendSMSResponse = iSMSService.executeSendSms(buildContent, listCardInfoResponse.getData().get(0).getPhoneEpin(),
                verifyPaymentRequest.getRequestorTransId(), esbSystemEcomLog.getAppId());
            if (sendSMSResponse.getResCode().getErrorCode().equals(ErrorMessage.FAIL.label)) {
                esbSystemEcomLog.setErrorCode(Response.OTHER_ERROR.getResponseCode());
                esbSystemEcomLog.setErrorDesc(Response.OTHER_ERROR.getResponseMessage());
                esbSystemEcomLog.setStatus(ErrorMessage.OTHER_ERROR.label);
                esbSystemEcomLogService.save(esbSystemEcomLog);

                initVerifyPaymentRequest.setResponseCode(Response.OTHER_ERROR.getResponseCode());
                initVerifyPaymentRequest.setResponseMessage(Response.OTHER_ERROR.getResponseMessage());
                throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                    dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
            }


            esbSystemEcomLog.setErrorCode(Response.SUCCESSFULL.getResponseCode());
            esbSystemEcomLog.setErrorDesc(Response.SUCCESSFULL.getResponseMessage());
            esbSystemEcomLog.setStatus(ErrorMessage.SUCCESS.label);
            esbSystemEcomLogService.save(esbSystemEcomLog);

            initVerifyPaymentRequest.setResponseCode(Response.SUCCESSFULL.getResponseCode());
            initVerifyPaymentRequest.setResponseMessage(Response.SUCCESSFULL.getResponseMessage());
            return initVerifyPaymentResponse.initVerifyPaymentResponse(verifyPaymentRequest, initVerifyPaymentRequest,
                dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
        } catch (ExceptionVerifyPayment e) {
            throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest, dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
        } catch (Exception e) {
            log.error(verifyPaymentRequest.getRequestorTransId() + "-" + esbSystemEcomLog.getAppId() + " Exception Payment: " + e);
            initVerifyPaymentRequest.setResponseCode(Response.INTERNAL_ERROR.getResponseCode());
            initVerifyPaymentRequest.setResponseMessage(Response.INTERNAL_ERROR.getResponseMessage());
            throw new ExceptionVerifyPayment(verifyPaymentRequest, initVerifyPaymentRequest,
                dataVerifyPaymentRequest, esbSystemEcomLog.getAppId());
        }
    }

    public Boolean validateInput(VerifyPaymentRequest verifyPaymentRequest, DataVerifyPaymentRequest dataVerifyPaymentRequest) {
        if (Objects.isNull(verifyPaymentRequest.getRequestorCode())
            || Objects.isNull(verifyPaymentRequest.getRequestorPassword())
            || Objects.isNull(dataVerifyPaymentRequest.getCard().getNumber())
            || Objects.isNull(verifyPaymentRequest.getRequestorTransId())
            || Objects.isNull(verifyPaymentRequest.getSignature())
            || Objects.isNull(dataVerifyPaymentRequest.getTransaction().getDate())
            || Objects.isNull(dataVerifyPaymentRequest.getTransaction().getAmount())
            || Objects.isNull(dataVerifyPaymentRequest.getTransaction().getCurrency())) {
            return false;
        } else {
            return true;
        }
    }
}
