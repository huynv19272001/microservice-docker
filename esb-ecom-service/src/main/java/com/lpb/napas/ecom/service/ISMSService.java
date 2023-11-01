package com.lpb.napas.ecom.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import org.w3c.dom.Document;

public interface ISMSService {
    ResponseModel getResponseModelSendSMS(Document doc);

    ResponseModel executeSendSms(String buildContent, String phoneNumber,
                                 String requestorTransId, String appId);
}
