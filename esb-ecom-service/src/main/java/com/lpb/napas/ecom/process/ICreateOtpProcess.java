package com.lpb.napas.ecom.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.CreateOtpREQDTO;

public interface ICreateOtpProcess {
    ResponseModel executeCreateOtpRequest(CreateOtpREQDTO createOtpRequest, String requestorTransId, String appId);
}
