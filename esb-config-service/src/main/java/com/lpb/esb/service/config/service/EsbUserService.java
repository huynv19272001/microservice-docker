package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

/**
 * Created by tudv1 on 2021-11-17
 */
public interface EsbUserService {
    ResponseModel getAllUser(int pageNumber, int pageSize);
}
