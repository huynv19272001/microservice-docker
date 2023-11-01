package com.lpb.insurance.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.insurance.model.EsbRequestDTO;

public interface BaseService {
    ResponseModel executeFunction(EsbRequestDTO data);
}
