package com.lpb.napas.ecom.common;

import com.lpb.napas.ecom.dto.DataVerifyOtpRequest;
import com.lpb.napas.ecom.dto.InitVerifyOtpRequest;
import com.lpb.napas.ecom.dto.VerifyOtpRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionVerifyOtp extends RuntimeException {
    VerifyOtpRequest verifyOtpRequest;
    InitVerifyOtpRequest initVerifyOtpRequest;
    DataVerifyOtpRequest dataVerifyOtpRequest;
    String appId;
}
