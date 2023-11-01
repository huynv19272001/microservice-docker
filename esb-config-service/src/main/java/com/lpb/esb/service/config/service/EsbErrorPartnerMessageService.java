package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

public interface EsbErrorPartnerMessageService {
    ResponseModel getErrorMessage(String serviceId, String partnerId, String partnerCode);
}
