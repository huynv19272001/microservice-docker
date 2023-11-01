package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbService;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.PurchaseConfigApi;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.IInitTransactionProcess;
import com.lpb.napas.ecom.service.IEsbServiceService;
import com.lpb.napas.ecom.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j2
@Service
public class InitTransactionProcessImpl implements IInitTransactionProcess {
    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IEsbServiceService esbServiceService;

//    @Autowired
//    ISttmDatesService sttmDatesService;

    @Autowired
    PurchaseConfigApi purchaseConfigApi;

    @Override
    public ResponseModel executeInitTransactionRequest(TransactionDTO transactionDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<TransactionDTO> entity = new HttpEntity<>(transactionDTO, headers);
            String responseInitTransaction = restTemplate.exchange
                (serviceApiConfig.getTransactionInitTransaction(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponseModel = objectMapper.readValue(responseInitTransaction, ResponseModel.class);

            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (Exception e) {
            log.error(transactionDTO.getTransactionInfo().getTransactionId() + " Exception InitTransaction " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }

        log.info(transactionDTO.getTransactionInfo().getTransactionId() + " InitTransaction " + lpbResCode.getErrorDesc());
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }


    @Override
    public ResponseModel executeGetInitTransactionRequest(EsbSystemEcomLog esbSystemEcomLog) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String appId = esbSystemEcomLog.getAppId();
            String response = restTemplate.exchange
                (serviceApiConfig.getTransactionGetInitTransaction(), HttpMethod.GET, entity, String.class, appId).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponseModel = objectMapper.readValue(response, ResponseModel.class);
            List<TransactionPostInfoDTO> getTransactionInit = objectMapper.convertValue(
                dataResponseModel.getData(),
                new TypeReference<List<TransactionPostInfoDTO>>() {
                }
            );
            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(getTransactionInit);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (Exception e) {
            log.error(esbSystemEcomLog.getAppId() + " Exception GetDataInitTransaction " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        log.info(esbSystemEcomLog.getAppId() + " GetDataInitTransaction " + lpbResCode.getErrorDesc());
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel executeUploadTransferJrnRequest(UploadTransferJRNDTO uploadTransferJRNDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ResponseModel dataResponseModel = ResponseModel.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<UploadTransferJRNDTO> entity = new HttpEntity<>(uploadTransferJRNDTO, headers);
            String responseInitTransaction = restTemplate.exchange
                (serviceApiConfig.getTransactionUploadTransferJrn(), HttpMethod.POST, entity, String.class).getBody();
            dataResponseModel = objectMapper.readValue(responseInitTransaction, ResponseModel.class);
            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (HttpClientErrorException e) {
            try {
                dataResponseModel = objectMapper.readValue(e.getResponseBodyAsString(), ResponseModel.class);
                if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.AVAILABLE_AMOUNT.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.AVAILABLE_AMOUNT.label);
                    lpbResCode.setErrorDesc(ErrorMessage.AVAILABLE_AMOUNT.description);
                } else if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.DUPLICATE_MESSAGE.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.DUPLICATE_MESSAGE.label);
                    lpbResCode.setErrorDesc(ErrorMessage.DUPLICATE_MESSAGE.description);
                } else if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.INVALID_ACCOUNT_NUMBER.label)) {
                    lpbResCode.setErrorCode(ErrorMessage.INVALID_ACCOUNT_NUMBER.label);
                    lpbResCode.setErrorDesc(ErrorMessage.INVALID_ACCOUNT_NUMBER.description);
                } else {
                    lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                    lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
                }
            } catch (Exception ex) {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.error(uploadTransferJRNDTO.getFCubsHeader().getAppId() + " Get UploadTransferJrn: " + lpbResCode.getErrorDesc());
        } catch (Exception e) {
            log.error(uploadTransferJRNDTO.getFCubsHeader().getAppId() + " Exception UploadTransferJrn " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        log.info(uploadTransferJRNDTO.getFCubsHeader().getAppId() + " UploadTransferJrn " + lpbResCode.getErrorDesc());
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    public List<PostInfoDTO> initListPostInfoDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                                 CardInfoDTO cardInfoDTO,
                                                 GetAvlBalanceRESDTO getAvlBalanceRESDTO) {
        EsbService esbService = esbServiceService.getEsbServiceByServiceId(serviceConfig.getServiceId());
        List<PostInfoDTO> listPostInfoDTO = new ArrayList<>();
        PostInfoDTO accPartner = PostInfoDTO.builder()
            .serviceType(esbService.getServiceType())
            .acNo(esbService.getCRHoldAcNo())
            .ccy(esbService.getCRHoldCCY())
            .branchCode(esbService.getCRHoldBRN())
            .drcrInd("C")
            .customerNo(cardInfoDTO.getCustomerNumber())
            .cardNo(dataVerifyPaymentRequest.getCard().getNumber())
            .lcyAmount(dataVerifyPaymentRequest.getTransaction().getAmount()).build();
        listPostInfoDTO.add(accPartner);

        PostInfoDTO postCr = PostInfoDTO.builder()
            .serviceType(esbService.getServiceType())
            .acNo(cardInfoDTO.getAccountNumber())
            //cần confirm lại xem dịch vụ có cho thanh toán bằng tiền tệ khác ko?
            .ccy(esbService.getCRHoldCCY())
            .branchCode(getAvlBalanceRESDTO.getBranchCode())
            .drcrInd("D")
            .customerNo(cardInfoDTO.getCustomerNumber())
            .cardNo(dataVerifyPaymentRequest.getCard().getNumber())
            .lcyAmount(dataVerifyPaymentRequest.getTransaction().getAmount()).build();
        listPostInfoDTO.add(postCr);

        return listPostInfoDTO;
    }

    public ServiceDTO initServiceDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest) {
        ServiceDTO serviceDTO = ServiceDTO.builder()
            .serviceId(serviceConfig.getServiceId())
            .merchantId(dataVerifyPaymentRequest.getMerchant().getId())
            .productCode(serviceConfig.getRequestTxn()).build();
        return serviceDTO;
    }

    public PartnerDTO initPartnerDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest, String appMsgId,
                                     VerifyPaymentRequest verifyPaymentRequest) {
        PartnerDTO partnerDTO = PartnerDTO.builder()
            .chanel(serviceConfig.getChannel())
            .txnCode(verifyPaymentRequest.getRequestorTransId())
            .txnRefNo(dataVerifyPaymentRequest.getTransaction().getTrace())
            .txnDatetime(dataVerifyPaymentRequest.getTransaction().getDate()).build();
        return partnerDTO;
    }

//    public AccountInfoDTO initAccountInfoDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest, DebitCardInfoResponse debitCardInfoResponse) {
//        EsbService esbService = esbServiceService.getEsbServiceByServiceId(serviceConfig.getServiceId());
//        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
//        accountInfoDTO.setAcy_avl_bal(null);
//        accountInfoDTO.setAccount_name(dataVerifyPaymentRequest.getAccount().getName());
//        accountInfoDTO.setAcc_class("V0DNTN");
//        accountInfoDTO.setAccount_desc(null);
//        accountInfoDTO.setAuth_stat(null);
//        accountInfoDTO.setUidvalue(dataVerifyPaymentRequest.getAccount().getPersonalId());
//        accountInfoDTO.setBranch_code(debitCardInfoResponse.getBranchCode());
//        accountInfoDTO.setAccount_no(dataVerifyPaymentRequest.getAccount().getNumber());
//        accountInfoDTO.setMobile_no(dataVerifyPaymentRequest.getAccount().getPhoneNumber());
//        accountInfoDTO.setOther(null);
//        accountInfoDTO.setCr_dr(null);
//        accountInfoDTO.setEmail(dataVerifyPaymentRequest.getAccount().getEmail());
//        accountInfoDTO.setRecord_stat(null);
//        accountInfoDTO.setDob(null);
//        accountInfoDTO.setMaker_id(null);
//        //cần confirm lại xem dịch vụ có cho thanh toán bằng tiền tệ khác ko?
//        accountInfoDTO.setCcy(esbService.getDRHoldCYY());
//        accountInfoDTO.setProvider_id(null);
//        accountInfoDTO.setAccount_id(null);
//        accountInfoDTO.setAddress(null);
//        accountInfoDTO.setCustomer_no(debitCardInfoResponse.getAccountNumber());
//        return accountInfoDTO;
//    }

    public TransactionInfoDTO initTransactionInfoDTO(EsbSystemEcomLog esbSystemEcomLog,
                                                     DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                                     CardInfoDTO cardInfoDTO, GetAvlBalanceRESDTO getAvlBalanceRESDTO) {
        Date date = new Date();
        EsbService esbService = esbServiceService.getEsbServiceByServiceId(serviceConfig.getServiceId());
        TransactionInfoDTO transactionInfoDTO = TransactionInfoDTO.builder()
            .transactionId(esbSystemEcomLog.getAppId())
            .trnBrn(esbService.getDRHoldBRN())
            .trnDesc(dataVerifyPaymentRequest.getTransaction().getInfo())
            .customerNo(getAvlBalanceRESDTO.getCustomerNumber())
            .confirmTrn("N")
            .valueDt(DateUtils.convertSqlTimeStampToStr
                (date))
            .userId(serviceConfig.getUserNapas()).build();
        return transactionInfoDTO;
    }

    public TransactionDTO initTransactionDTO(EsbSystemEcomLog esbSystemEcomLog, VerifyPaymentRequest verifyPaymentRequest,
                                             CardInfoDTO cardInfoDTO,
                                             DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                             GetAvlBalanceRESDTO getAvlBalanceRESDTO) {
        List<PostInfoDTO> listPostInfoDTO = initListPostInfoDTO(dataVerifyPaymentRequest, cardInfoDTO, getAvlBalanceRESDTO);
        PartnerDTO partnerDTO = initPartnerDTO(dataVerifyPaymentRequest, esbSystemEcomLog.getMsgId(), verifyPaymentRequest);
        TransactionInfoDTO transactionInfoDTO = initTransactionInfoDTO(esbSystemEcomLog, dataVerifyPaymentRequest,
            cardInfoDTO, getAvlBalanceRESDTO);
        ServiceDTO serviceDTO = initServiceDTO(dataVerifyPaymentRequest);
        //AccountInfoDTO accountInfoDTO = initAccountInfoDTO(verifyPaymentRequest.getData(), debitCardInfoResponse.getData());

        TransactionDTO transactionDTO = TransactionDTO.builder()
            .transactionInfo(transactionInfoDTO)
            .postInfo(listPostInfoDTO)
            .service(serviceDTO)
            .partnerInfo(partnerDTO)
            .build();
        //transactionDTO.setAccount_info(accountInfoDTO);

        return transactionDTO;
    }

    public UploadTransferJRNDTO initUploadTransferJRNDTO(List<TransactionPostInfoDTO> listTransactionPostInfoDTO,
                                                         EsbSystemEcomLog esbSystemEcomLog, PurchaseRequest purchaseRequest) {
        Date date = new Date();
        UploadTransferIO uploadTransferIO = UploadTransferIO.builder()
            .description(listTransactionPostInfoDTO.get(0).getTrnDesc())
//            .valueDt(DateUtils.convertSqlTimeStampToStr(sttmDatesService.getSttmDatesByBranchCode(listTransactionPostInfoDTO.get(0).getTrnBranch()).getToday()))
            .valueDt(DateUtils.convertSqlTimeStampToStr(date))
            .sourceCode(purchaseConfigApi.getSourceCode())
            .txnCode(purchaseConfigApi.getTxnCode())
            .makerId(purchaseConfigApi.getMakerId())
            .checkerId(purchaseConfigApi.getCheckerId()).build();

        FCubsHeaderDTO fCubsHeader = FCubsHeaderDTO.builder()
            .msgId("EC" + esbSystemEcomLog.getAppId())
            .appId(esbSystemEcomLog.getAppId())
            .source(serviceConfig.getHeaderSource())
            .password(serviceConfig.getHeaderPassword())
            .ubsComp(serviceConfig.getHeaderUbscomp())
            .userId(serviceConfig.getHeaderUserId())
            .moduleId(serviceConfig.getHeaderModuleId())
            .service(serviceConfig.getHeaderService())
            .operation(serviceConfig.getHeaderOperation())
            .functionId(serviceConfig.getHeaderFunctionId())
            .build();

        UploadTransferJRNDTO uploadTransferJRNDTO = UploadTransferJRNDTO.builder().build();

        List<EntryDTO> listEntry = new ArrayList<>();
        for (TransactionPostInfoDTO transactionPostInfoDTO : listTransactionPostInfoDTO) {
            EntryDTO entry = EntryDTO.builder()
                .accountNumber(transactionPostInfoDTO.getAccNo())
                .accountBranch(transactionPostInfoDTO.getAccBranch())
                .accountCCY(transactionPostInfoDTO.getAccCcy())
                .drcrIndicator(transactionPostInfoDTO.getDrcrInd()).build();
            if (transactionPostInfoDTO.getAccNo().trim().length() != 12) {
                entry.setAccountType("G");
            } else {
                entry.setAccountType("A");
            }
            if (transactionPostInfoDTO.getDrcrInd().equals("D")) {
                fCubsHeader.setBranch(transactionPostInfoDTO.getAccBranch());
                uploadTransferIO.setBranchCode(transactionPostInfoDTO.getAccBranch());
            }
            entry.setLcyAmount(transactionPostInfoDTO.getLcyAmount());
            listEntry.add(entry);
        }
        uploadTransferIO.setListEntry(listEntry);
        uploadTransferJRNDTO.setFCubsHeader(fCubsHeader);
        uploadTransferJRNDTO.setUploadTransferIO(uploadTransferIO);

        return uploadTransferJRNDTO;
    }
}
