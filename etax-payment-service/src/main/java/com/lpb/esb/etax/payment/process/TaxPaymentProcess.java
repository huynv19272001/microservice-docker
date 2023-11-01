/**
 * @author Trung.Nguyen
 * @date 10-May-2022
 * */
package com.lpb.esb.etax.payment.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lpb.esb.etax.payment.config.ServiceApiConfig;
import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.config.GeneralTransConfig;
import com.lpb.esb.etax.payment.model.config.UploadTransConfig;
import com.lpb.esb.etax.payment.model.data.EntryInfo;
import com.lpb.esb.etax.payment.model.data.FcubsHeader;
import com.lpb.esb.etax.payment.model.data.InitTransaction;
import com.lpb.esb.etax.payment.model.data.OtpInfo;
import com.lpb.esb.etax.payment.model.data.TransAccountInfo;
import com.lpb.esb.etax.payment.model.data.UploadTransferIO;
import com.lpb.esb.etax.payment.model.data.UploadTransferInfo;
import com.lpb.esb.etax.payment.model.response.ConfrmTransResponse;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;
import com.lpb.esb.etax.payment.model.response.InitTaxPaymentResponse;
import com.lpb.esb.etax.payment.util.FlexCubsUtils;
import com.lpb.esb.etax.payment.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.ResponseModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TaxPaymentProcess {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ServiceApiConfig serviceApiConfig;
	@Autowired
	private OtpProcess otpProcess;
	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private FlexCubsUtils flexMsgUtils;

	public InitTaxPaymentResponse initTaxPayment(InitTransaction initTransaction, OtpInfo otpInfo) {
		InitTaxPaymentResponse response = new InitTaxPaymentResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
		try {
			log.info("init-transaction, request body: " + objectMapper.writeValueAsString(initTransaction));
			// Create JSON headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			// Build the rest request
			HttpEntity<InitTransaction> restRequest = new HttpEntity<>(initTransaction, headers);
			// Call Rest API
			ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getTransInitUrl(), restRequest);
			if ((restResponse == null)) {
				log.error("init-transaction, error: He thong khong nhan duoc response");
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Convert JSON to object
			String body = restResponse.getBody();
			log.info("init-transaction, response body: " + body);
			ResponseModel<String> initTransRes = objectMapper.readValue(body, new TypeReference<ResponseModel<String>>() {});
			// Check response object
			if ((initTransRes == null) || (initTransRes.getResCode() == null)) {
				log.error("init-transaction, error: Response khong hop le");
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// If the initialization of the transaction fails, immediately return.
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(initTransRes.getResCode().getErrorCode())) {
				response.setResponseCode(initTransRes.getResCode().getErrorCode());
				response.setResponseDesc(initTransRes.getResCode().getErrorDesc());
                if ("Duplicate Request Ref NO".equalsIgnoreCase(initTransRes.getResCode().getErrorDesc())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_116);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_116);
                }
				return response;
			}
			/**
			 * Create OTP and send OTP via sms
			 * */
			String transId = initTransaction.getTransactionInfo().getTransId();
//			OtpInfo otpInfo = OtpInfo.builder()
//								.userId(UploadTransConfig.ETAX_USERID)
//								.appMsgId(transId)
//								.mobileNo("").build();
            otpInfo.setUserId(UploadTransConfig.ETAX_USERID);
            otpInfo.setAppMsgId(transId);
			DefaultResponse result = otpProcess.createOtp(otpInfo);
			// If the creation of OTP fails, immediately return.
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
				response.setResponseCode(result.getResponseCode());
				response.setResponseDesc(result.getResponseDesc());
				return response;
			}
			response.setTransId(transId);
			response.setVerificationType(GeneralTransConfig.VERIFICATION_SMS_OTP);
			response.setSmartOTPUnlockCode("");
			// return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
			return response;
		} catch (RestClientException ex) {
			ex.printStackTrace();
			log.error("init-transaction, exception: "+ ex.getMessage());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			log.error("init-transaction, exception: "+ ex.getMessage());
		}
		return response;
	}

	public List<TransAccountInfo> getAccount4UploadTransferJrn(String transId) {
		try {
			// Create JSON headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			// Build the rest request
			HttpEntity<String> restRequest = new HttpEntity<String>(headers);
			// Call Rest API
			ResponseEntity<String> restResponse = restClientUtils.doGet(serviceApiConfig.getTransGetUrl() + "/" + transId, restRequest);
			if ((restResponse == null)) {
				log.error("get-transaction, error: He thong khong nhan duoc response");
				return null;
			}
			// Convert JSON to object
			String body = restResponse.getBody();
			log.info("get-transaction, response body: " + body);
			ResponseModel<List<TransAccountInfo>> getTransRes = objectMapper.readValue(body, new TypeReference<ResponseModel<List<TransAccountInfo>>>() {});
			// Check response object
			if ((getTransRes == null) || (getTransRes.getResCode() == null)) {
				log.error("get-transaction, error: Response khong hop le");
				return null;
			}
			// Check response code
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(getTransRes.getResCode().getErrorCode()))
				return null;
			// return
			List<TransAccountInfo> accountList = (List<TransAccountInfo>)getTransRes.getData();
			return accountList;
		} catch (RestClientException ex) {
			ex.printStackTrace();
			log.error("get-transaction, exception: " + ex);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			log.error("get-transaction, exception: " + ex);
		}
		return null;
	}

	public ConfrmTransResponse confirmTaxPayment(UploadTransferInfo uploadTransferInfo) {
		ConfrmTransResponse response = new ConfrmTransResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
		try {
			log.info("confirm-transaction, request body: " + objectMapper.writeValueAsString(uploadTransferInfo));
			// Create JSON headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			// Build the rest request
			HttpEntity<UploadTransferInfo> restRequest = new HttpEntity<>(uploadTransferInfo, headers);
			// Call Rest API
			ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getTransConfUrl(), restRequest);
			if ((restResponse == null)) {
				log.error("confirm-transaction, error: He thong khong nhan duoc response");
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Convert JSON to object
			String body = restResponse.getBody();
			log.info("confirm-transaction, response body: " + body);
			ResponseModel<String> confirmTransRes = objectMapper.readValue(body, new TypeReference<ResponseModel<String>>() {});
			// Check response object
			if ((confirmTransRes == null) || (confirmTransRes.getResCode() == null)) {
				log.error("confirm-transaction, error: Response khong hop le");
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// If the conformation of the transaction fails, immediately return.
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(confirmTransRes.getResCode().getErrorCode())) {
				response.setResponseCode(confirmTransRes.getResCode().getErrorCode());
				response.setResponseDesc(confirmTransRes.getResCode().getErrorDesc());
                if ("46".equals(confirmTransRes.getResCode().getErrorCode())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_01);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_01);
                }
				return response;
			}
			// return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
			return response;
		} catch (RestClientException ex) {
			ex.printStackTrace();
			log.error("confirm-transaction, exception: "+ ex.getMessage());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			log.error("confirm-transaction, exception: "+ ex.getMessage());
		}
		return response;
	}

    /**
     * @author Trung.Nguyen
     * @date 20-Sep-2022
     * description:
     * */
    public ConfrmTransResponse uploadTransferCitad(UploadTransferInfo uploadTransferInfo) {
        ConfrmTransResponse response = new ConfrmTransResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
        try {
            log.info("upload-transfer-citad, request body: " + objectMapper.writeValueAsString(uploadTransferInfo));
            // Create JSON headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // Build the rest request
            HttpEntity<UploadTransferInfo> restRequest = new HttpEntity<>(uploadTransferInfo, headers);
            // Call Rest API
            ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getTransCtadUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("upload-transfer-citad, error: He thong khong nhan duoc response");
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("upload-transfer-citad, response body: " + body);
            ResponseModel<String> confirmTransRes = objectMapper.readValue(body, new TypeReference<ResponseModel<String>>() {});
            // Check response object
            if ((confirmTransRes == null) || (confirmTransRes.getResCode() == null)) {
                log.error("upload-transfer-citad, error: Response khong hop le");
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // If the conformation of the transaction fails, immediately return.
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(confirmTransRes.getResCode().getErrorCode())) {
                response.setResponseCode(confirmTransRes.getResCode().getErrorCode());
                response.setResponseDesc(confirmTransRes.getResCode().getErrorDesc());
                if ("46".equals(confirmTransRes.getResCode().getErrorCode())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_01);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_01);
                }
                return response;
            }
            // return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("upload-transfer-citad, exception: "+ ex.getMessage());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("upload-transfer-citad, exception: "+ ex.getMessage());
        }
        return response;
    }

	public UploadTransferInfo buildUploadTransferReq(String transId, List<TransAccountInfo> listAccount) {
		// Build header
		FcubsHeader fcubsHeader = FcubsHeader.builder()
									.source(UploadTransConfig.FCUBS_HEADER_SOURCE)
									.ubsComp(UploadTransConfig.FCUBS_HEADER_UBSCOMP)
									.branch(UploadTransConfig.FCUBS_HEADER_BRANCH)
									.userId(UploadTransConfig.FCUBS_HEADER_USERID)
									.password(UploadTransConfig.FCUBS_HEADER_PASSWORD)
									.msgId(flexMsgUtils.getMsgIdByDate())
									.moduleId(UploadTransConfig.FCUBS_HEADER_MODULEID)
									.service(UploadTransConfig.FCUBS_HEADER_SERVICE)
									.operation(UploadTransConfig.FCUBS_HEADER_OPERATION)
									.appId(transId).build();
		/**
		 * Build upload transfer body
		 * */
        String drBranchCode = "";
        String trnDesc = "";
		// build entry info
		List<EntryInfo> listEntry = new ArrayList<EntryInfo>();
		for (TransAccountInfo account : listAccount) {
			EntryInfo entryInfo = EntryInfo.builder()
									.accountNumber(account.getAccNo())
									.accountBranch(account.getAccBranch())
									.accountCCY(account.getAccCcy())
									.accountType("A")
									.drcrIndicator(account.getDrcrInd())
									.lcyAmount(account.getLcyAmount())
                                    .senderInstAccount(account.getSenderInstAccount())
                                    .receiverName(account.getReceiverName())
                                    .receiverAddress(account.getReceiverAddress())
                                    .receiverAccount(account.getReceiverAccount())
                                    .receiverCode(account.getReceiverCode()).build();
			// appended to the entry list
			listEntry.add(entryInfo);
            // Get branchCode and transaction description of debit account
            if (UploadTransConfig.DR_INDICATOR.equals(account.getDrcrInd())) {
                drBranchCode = account.getAccBranch();
                trnDesc = account.getTrnDesc();
            }
		}
		// build body
		UploadTransferIO uploadTransferIO = UploadTransferIO.builder()
												.sourceCode(UploadTransConfig.UPLOAD_TRANSFER_IO_SOURCECODE)
												.branchCode(drBranchCode != null? drBranchCode : UploadTransConfig.UPLOAD_TRANSFER_IO_BRANCHCODE)
												.description(trnDesc != null ? trnDesc : UploadTransConfig.UPLOAD_TRANSFER_IO_DESCRIPTION)
												.txnCode(UploadTransConfig.UPLOAD_TRANSFER_IO_TXNCODE)
												.valueDt(flexMsgUtils.getTransDate("dd/MM/yyyy"))
												.makerId(UploadTransConfig.UPLOAD_TRANSFER_IO_MAKERID)
												.checkerId(UploadTransConfig.UPLOAD_TRANSFER_IO_CHECKERID)
												.listEntry(listEntry).build();
		// set upload transfer info
		UploadTransferInfo uploadTransferInfo = UploadTransferInfo.builder()
													.fcubsHeader(fcubsHeader)
													.uploadTransferIO(uploadTransferIO).build();
		// return
		return uploadTransferInfo;
	}

}
