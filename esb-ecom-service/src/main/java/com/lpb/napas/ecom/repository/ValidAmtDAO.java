package com.lpb.napas.ecom.repository;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.InitValidAmtRequest;

public interface ValidAmtDAO {
    ResponseModel validAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);

    ResponseModel validCountAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);

    ResponseModel validAmtOneTxn(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId);
}
