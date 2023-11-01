package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

public interface EsbPartnerAccountService {
    ResponseModel findPartnerAccount(String serviceId, String productCode, String providerId);
}
