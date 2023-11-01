package com.lpb.napas.ecom.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.napas.ecom.dto.VerifyPaymentRequest;
import com.lpb.napas.ecom.dto.VerifyPaymentResponse;

public interface IVerifyPaymentProcess {
    VerifyPaymentResponse initVerifyPaymentProcess(VerifyPaymentRequest verifyPaymentRequest) throws JsonProcessingException;
}
