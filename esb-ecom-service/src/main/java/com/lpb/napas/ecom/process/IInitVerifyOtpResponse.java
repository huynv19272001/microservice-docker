package com.lpb.napas.ecom.process;

import com.lpb.napas.ecom.dto.DataVerifyOtpRequest;
import com.lpb.napas.ecom.dto.InitVerifyOtpRequest;
import com.lpb.napas.ecom.dto.VerifyOtpRequest;
import com.lpb.napas.ecom.dto.VerifyOtpResponse;

public interface IInitVerifyOtpResponse {
    VerifyOtpResponse initVerifyOtpResponse(VerifyOtpRequest verifyOtpRequest,
                                            InitVerifyOtpRequest initVerifyOtpRequest,
                                            DataVerifyOtpRequest dataVerifyOtpRequest,
                                            String appId) throws Exception;
}
