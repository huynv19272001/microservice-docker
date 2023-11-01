package com.lpb.napas.ecom.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.VerifyOtpREQDTO;
import com.lpb.napas.ecom.dto.VerifyOtpRequest;
import com.lpb.napas.ecom.dto.VerifyOtpResponse;

public interface IVerifyOtpProcess {
    ResponseModel executeVerifyOtpRequest(VerifyOtpREQDTO verifyOtpREQDTO, String requestorTransId, String appId) throws JsonProcessingException;

    VerifyOtpResponse verifyOtpProcess(VerifyOtpRequest verifyOtpRequest) throws JsonProcessingException;
}
