package com.lpb.napas.ecom.common;

import com.lpb.napas.ecom.dto.DataVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.InitVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.VerifyPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionVerifyPayment extends RuntimeException {
    VerifyPaymentRequest verifyPaymentRequest;
    InitVerifyPaymentRequest initVerifyPaymentRequest;
    DataVerifyPaymentRequest dataVerifyPaymentRequest;
    String appId;
}
