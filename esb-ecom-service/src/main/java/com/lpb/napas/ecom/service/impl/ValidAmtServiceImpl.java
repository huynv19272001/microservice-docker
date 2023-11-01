package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.dto.CardInfoDTO;
import com.lpb.napas.ecom.dto.DataVerifyPaymentRequest;
import com.lpb.napas.ecom.dto.InitValidAmtRequest;
import com.lpb.napas.ecom.model.EsbLimitAmt;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.repository.ValidAmtDAO;
import com.lpb.napas.ecom.service.ValidAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lpb.esb.service.common.model.response.ResponseModel;

@Service
public class ValidAmtServiceImpl implements ValidAmtService {
    @Autowired
    ValidAmtDAO validAmtDAO;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel validAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        return validAmtDAO.validAmtOneDay(initValidAmtRequest, requestorTransId, appId);
    }

    @Override
    public ResponseModel validAmtOneTxn(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        return validAmtDAO.validAmtOneTxn(initValidAmtRequest, requestorTransId, appId);
    }

    @Override
    public ResponseModel validCountAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        return validAmtDAO.validCountAmtOneDay(initValidAmtRequest, requestorTransId, appId);
    }

    @Override
    public InitValidAmtRequest initValidAmtRequest(CardInfoDTO cardInfo,
                                                   DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                                   EsbLimitAmt esbLimitAmt) {
        InitValidAmtRequest initValidAmtRequest = new InitValidAmtRequest();
        initValidAmtRequest.setServiceId(serviceConfig.getServiceId());
        initValidAmtRequest.setAmt(Integer.parseInt(dataVerifyPaymentRequest.getTransaction().getAmount()));
        initValidAmtRequest.setCustomerNo(cardInfo.getCustomerNumber());
        initValidAmtRequest.setMaxAmt(esbLimitAmt.getMaxAmt());
        initValidAmtRequest.setMinAmt(esbLimitAmt.getMinAmt());
        return initValidAmtRequest;
    }
}
