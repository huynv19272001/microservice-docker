package com.lpb.esb.service.sms.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.model.request.SmsCategoryRequest;

/**
 * Created by tudv1 on 2022-05-24
 */
public interface SmsGatewayProcess {
    EsbSmsRequest buildMsgSmsGateway(ServiceInfo serviceInfo, SmsCategoryRequest request, String hasRole);

    EsbSmsRequest buildMsgSmsRequest(ServiceInfo serviceInfo, SmsCategoryRequest request);

    EsbSmsRequest buildMsgSmsRequestVnp(ServiceInfo serviceInfo, SmsCategoryRequest request);

    ResponseModel executeRequestSendSms(EsbSmsRequest esbSmsRequest, ServiceInfo serviceInfo) throws JsonProcessingException;
}
