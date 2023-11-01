package com.lpb.napas.ecom.process;

import com.lpb.napas.ecom.dto.DataVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.InitVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.VerifyPaymentRequest;
import com.lpb.napas.ecom.dto.VerifyPaymentResponse;

public interface IInitVerifyPaymentResponse {
    VerifyPaymentResponse initVerifyPaymentResponse(VerifyPaymentRequest verifyPaymentRequest,
                                                    InitVerifyPaymentRequest initVerifyPaymentRequest,
                                                    DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                                    String appId) throws Exception;
}
