/**
 * @author Trung.Nguyen
 * @date 24-May-2022
 * */
package com.lpb.esb.etax.payment.process;

import java.util.Collections;
import java.util.List;

import com.lpb.esb.etax.payment.model.data.LpbAccountDetail;
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
import com.lpb.esb.etax.payment.model.config.GeneralTransConfig;
import com.lpb.esb.etax.payment.config.ServiceApiConfig;
import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.data.LpbAccount;
import com.lpb.esb.etax.payment.model.data.LpbAccountInfo;
import com.lpb.esb.etax.payment.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class PostAccountProcess {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ServiceApiConfig serviceApiConfig;
	@Autowired
	private RestClientUtils restClientUtils;

	public ResponseModel<LpbAccount> inquiryAccount(String accountNumber, String accountType) {
		ResponseModel<LpbAccount> response = new ResponseModel<>();
		LpbResCode resCode = new LpbResCode();
		resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_99);
    	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_99);
		response.setResCode(resCode);
		try {
			// Create JSON headers
        	HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(MediaType.APPLICATION_JSON);
        	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        	// Build the rest request
        	LpbAccountInfo accountInfo = LpbAccountInfo.builder()
											.accountNumber(accountNumber)
											.accountType(accountType).build();
        	HttpEntity<LpbAccountInfo> restRequest = new HttpEntity<>(accountInfo, headers);
            ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getFlexQueryAccUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("query-account-info, error: He thong khong nhan duoc response");
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
            	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("query-account-info, response body: " + body);
            ResponseModel<List<LpbAccount>> accountInqRes = objectMapper.readValue(body, new TypeReference<ResponseModel<List<LpbAccount>>>() {});
            // Check response object
            if ((accountInqRes == null) || (accountInqRes.getResCode() == null)) {
            	log.error("query-account-info, error: Response khong hop le");
            	resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
            	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
            	return response;
            }
            // Check response code
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(accountInqRes.getResCode().getErrorCode())) {
            	resCode.setErrorCode(accountInqRes.getResCode().getErrorCode());
            	resCode.setErrorDesc(accountInqRes.getResCode().getErrorDesc());
                return response;
            }
            // get account info from list
            List<LpbAccount> accountList = (List<LpbAccount>) accountInqRes.getData();
            for (LpbAccount account : accountList) {
                if ("O".equals(account.getRecordStat())) {
                	/**
                     * Check account class
                     * */
                    if (!GeneralTransConfig.ACCOUNT_CLASS_V0CNTN.equals(account.getAccountClass())
                        && !GeneralTransConfig.ACCOUNT_CLASS_V0ID44.equals(account.getAccountClass())) {
                    	resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_01);
                    	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_01);
                        return response;
                    }
                    /**
                     * Check debit account status:
                     *      AuthStat=U: the account has not been approved
                     *      AcStatFrozen=Y: the account has been frozen
                     *      AcStatNoDr=Y: the account has been banned from debit
                     * */
                    if ("U".equalsIgnoreCase(account.getAuthStat()) || "Y".equalsIgnoreCase(account.getAcStatFrozen()) || "Y".equalsIgnoreCase(account.getAcStatNoDr())) {
                        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_01);
                        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_01);
                        return response;
                    }
                	response.setData(account);
                	resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_00);
            		resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_00);
                	return response;
                }
            }
            // return
            resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_056);
        	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_056);
		} catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("query-account-info, exception: "+ ex);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("query-account-info, exception: "+ ex);
        }
		return response;
	}

    /**
     * @author: Trung.Nguyen
     * @since: 29-Sep-2022
     * */
    public ResponseModel<LpbAccountDetail> inquiryAccountDetail(String accountNumber) {
        ResponseModel<LpbAccountDetail> response = new ResponseModel<>();
        LpbResCode resCode = new LpbResCode();
        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_99);
        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_99);
        response.setResCode(resCode);
        try {
            // Create JSON headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // Build the rest request
            LpbAccountInfo accountInfo = LpbAccountInfo.builder()
                .accountNumber(accountNumber).build();
            HttpEntity<LpbAccountInfo> restRequest = new HttpEntity<>(accountInfo, headers);
            ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getFlexQueryAccDtlUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("query-account-detail, error: He thong khong nhan duoc response");
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("query-account-detail, response body: " + body);
            ResponseModel<List<LpbAccountDetail>> accountInqRes = objectMapper.readValue(body, new TypeReference<ResponseModel<List<LpbAccountDetail>>>() {});
            // Check response object
            if ((accountInqRes == null) || (accountInqRes.getResCode() == null)) {
                log.error("query-account-detail, error: Response khong hop le");
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Check response code
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(accountInqRes.getResCode().getErrorCode())) {
                resCode.setErrorCode(accountInqRes.getResCode().getErrorCode());
                resCode.setErrorDesc(accountInqRes.getResCode().getErrorDesc());
                return response;
            }
            // get account info from list
            List<LpbAccountDetail> accountList = (List<LpbAccountDetail>) accountInqRes.getData();
            for (LpbAccountDetail account : accountList) {
                if ("O".equals(account.getRecordStat())) {
                    /**
                     * Check account class
                     * */
                    if (!GeneralTransConfig.ACCOUNT_CLASS_V0CNTN.equals(account.getAccountClass())
                        && !GeneralTransConfig.ACCOUNT_CLASS_V0ID44.equals(account.getAccountClass())) {
                        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_01);
                        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_01);
                        return response;
                    } else if (GeneralTransConfig.ACCOUNT_CLASS_V0CNTN.equals(account.getAccountClass()) && "VVONLINE".equals(account.getChannel())) {
                        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_01);
                        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_01);
                        return response;
                    }
                    /**
                     * Check debit account status:
                     *      AuthStat=U: the account has not been approved
                     *      IsFrozen=Y: the account has been frozen
                     *      CanDebit=N: the account has been banned from debit
                     * */
                    if ("U".equalsIgnoreCase(account.getAuthStat()) || "Y".equalsIgnoreCase(account.getIsFrozen()) || "N".equalsIgnoreCase(account.getCanDebit())) {
                        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_01);
                        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_01);
                        return response;
                    }
                    response.setData(account);
                    resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_00);
                    resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_00);
                    return response;
                }
            }
            // return
            resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_056);
            resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_056);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("query-account-info, exception: "+ ex);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("query-account-info, exception: "+ ex);
        }
        return response;
    }

}
