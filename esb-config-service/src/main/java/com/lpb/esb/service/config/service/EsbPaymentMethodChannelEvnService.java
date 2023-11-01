package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

public interface EsbPaymentMethodChannelEvnService {
    ResponseModel findPaymentMethodChannelEvn(String productCode, String paymentMethod, String channel, String billPrint);
}
