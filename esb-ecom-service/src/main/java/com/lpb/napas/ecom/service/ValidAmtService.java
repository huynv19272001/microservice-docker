package com.lpb.napas.ecom.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.CardInfoDTO;
import com.lpb.napas.ecom.dto.DataVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.InitValidAmtRequest;
import com.lpb.napas.ecom.model.EsbLimitAmt;

public interface ValidAmtService {
    ResponseModel validAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);

    ResponseModel validAmtOneTxn(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);

    ResponseModel validCountAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);

    InitValidAmtRequest initValidAmtRequest(CardInfoDTO cardInfoDTO,
                                            DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                            EsbLimitAmt esbLimitAmt);
}
