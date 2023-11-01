package com.esb.transaction.service;

import com.esb.transaction.dto.CreateOtpREQDTO;
import com.esb.transaction.dto.VerifyOtpREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface IVerifyOtpService {
    ResponseModel createOtp(CreateOtpREQDTO createOtpREQ);

    ResponseModel verifyOtp(VerifyOtpREQDTO verifyOtpREQ);
}
