/**
 * @author Trung.Nguyen
 * @date 26-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.process;

import java.util.Collections;

import com.lpb.esb.etax.inquiry.model.data.SmsInfo;
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
import com.lpb.esb.etax.inquiry.config.ServiceApiConfig;
import com.lpb.esb.etax.inquiry.config.SimulatorConfig;
import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.config.GeneralTransConfig;
import com.lpb.esb.etax.inquiry.model.data.OtpInfo;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;
import com.lpb.esb.etax.inquiry.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.ResponseModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OtpProcess {

	@Autowired
	private ServiceApiConfig serviceApiConfig;
	@Autowired
	private SimulatorConfig simulatorConfig;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RestClientUtils restClientUtils;
    @Autowired
    private SmsProcess smsProcess;

	public DefaultResponse createOtp(OtpInfo otpInfo) {
		DefaultResponse response = new DefaultResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
        try {
        	/**
        	 * For Testing environment, OTP default is 123456
        	 * */
//        	if (GeneralTransConfig.ENV_TEST.equals(simulatorConfig.getEnvDo())) {
//	        	response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
//	    		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
//	        	return response;
//			}
        	// Create JSON headers
        	HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(MediaType.APPLICATION_JSON);
        	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        	// Build the rest request
        	HttpEntity<OtpInfo> restRequest = new HttpEntity<>(otpInfo, headers);
        	// Call Rest API
        	ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getOtpCreateUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("create-otp, error: He thong khong nhan duoc response");
            	response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("create-otp, response body: " + body);
            ResponseModel<String> otpCreationRes = objectMapper.readValue(body, new TypeReference<ResponseModel<String>>() {});
            // Check response object
            if ((otpCreationRes == null) || (otpCreationRes.getResCode() == null)) {
            	log.error("create-otp, error: Response khong hop le");
            	response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Check response code
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(otpCreationRes.getResCode().getErrorCode())) {
                response.setResponseCode(otpCreationRes.getResCode().getErrorCode());
                response.setResponseDesc(otpCreationRes.getResCode().getErrorDesc());
                return response;
            }
            // Get OTP from response
            String otp = (String) otpCreationRes.getData();
            /**
             * Send OTP Code to Mobile SMS
             * */
            String otpSms = String.format(GeneralTransConfig.LINK_OTP_SMS, otp);
            SmsInfo smsInfo = SmsInfo.builder()
                        .msgId(otpInfo.getAppMsgId())
                        .mobileNumber(otpInfo.getMobileNo())
                        .content(otpSms)
                        .branchCode("001")
                        .category("ETAX_SMS_LPB").build();
            response = smsProcess.sendSms(smsInfo);
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode())) {
                return response;
            }
            // return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("create-otp, exception: "+ ex.getMessage());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("create-otp, exception: "+ ex.getMessage());
        }
        return response;
	}

}
