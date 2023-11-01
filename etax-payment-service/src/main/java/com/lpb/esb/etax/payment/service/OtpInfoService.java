package com.lpb.esb.etax.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.config.UploadTransConfig;
import com.lpb.esb.etax.payment.model.data.OtpInfo;
import com.lpb.esb.etax.payment.model.data.TransConfirmation;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;
import com.lpb.esb.etax.payment.process.OtpProcess;
import com.lpb.esb.etax.payment.util.RequestUtils;

@Service
public class OtpInfoService {

	@Autowired
	private OtpProcess otpProcess;
	@Autowired
	private RequestUtils requestUtils;

	public DefaultResponse verifyOtp(TransConfirmation transConfirmation) {
		DefaultResponse response = new DefaultResponse();
		
		// Check request
		response = requestUtils.validateTransConfirmation(transConfirmation);
		if (!EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode())) 
			return response;
		
		OtpInfo otpInfo = OtpInfo.builder()
							.appMsgId(transConfirmation.getTransId())
							.otpCode(transConfirmation.getOtpCode())
							.userId(UploadTransConfig.ETAX_USERID)
							.mobileNo("").build();
		response = otpProcess.verifyOtp(otpInfo);
		// return
		return response;
	}

}
