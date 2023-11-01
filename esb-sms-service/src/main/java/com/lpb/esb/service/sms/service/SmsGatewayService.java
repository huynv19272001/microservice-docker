package com.lpb.esb.service.sms.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.request.SmsCategoryRequest;

/**
 * Created by tudv1 on 2022-05-24
 */
public interface SmsGatewayService {
    ResponseModel smsGatewaySend(SmsCategoryRequest smsCategoryRequest);

//    ResponseModel sendSms(SmsCategoryRequest smsCategoryRequest);
}
