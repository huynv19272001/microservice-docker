package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

import java.util.List;

/**
 * Created by tudv1 on 2022-07-08
 */
public interface EsbSettleAccountService {
    ResponseModel findSettleAccount(String serviceId, List<String> providerId);
}
