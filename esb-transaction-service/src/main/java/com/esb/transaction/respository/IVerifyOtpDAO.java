package com.esb.transaction.respository;

import com.lpb.esb.service.common.model.response.ResponseModel;

public interface IVerifyOtpDAO {
    ResponseModel verifyOtp(String appMsgId, String userId,
                            String otpCode, int initOrAuthOtp);
}
