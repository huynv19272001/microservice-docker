package com.lpb.esb.service.sms.service;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-08-13
 */
@Service
public interface SmsSendService {
    ExecuteModel sendTelegram(String message, String chatId);

    ResponseModel sendSmsEsb(EsbSmsRequest esbSmsRequest);

    ResponseModel sendSmsFtel(EsbSmsRequest esbSmsRequest);


    ResponseModel sendSmsVnpay(EsbSmsRequest esbSmsRequest);

    ResponseModel sendSmsVnpXml(EsbSmsRequest esbSmsRequest);
}
