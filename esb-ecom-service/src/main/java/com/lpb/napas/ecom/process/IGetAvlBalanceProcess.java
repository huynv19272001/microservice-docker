package com.lpb.napas.ecom.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;

public interface IGetAvlBalanceProcess {
    ResponseModel excuteGetAvlBalance(GetAvlBalanceREQDTO getAvlBalanceREQDTO, String requestorTransId, String appId);

    GetAvlBalanceREQDTO initGetAvlBalanceDTO(CardInfoDTO cardInfoDTO, EsbSystemEcomLog esbSystemEcomLog, VerifyPaymentRequest verifyPaymentRequest);

    Boolean checkAvlBalance(GetAvlBalanceRESDTO getAvlBalanceRESDTO,
                            DataVerifyPaymentRequest dataVerifyPaymentRequest);
}
