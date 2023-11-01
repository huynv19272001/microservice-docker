package com.lpb.insurance.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

public interface TokenService {
    ResponseModel getToken();
    ResponseModel executeGetToken();
}
