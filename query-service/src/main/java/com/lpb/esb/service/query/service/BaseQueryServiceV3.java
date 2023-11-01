package com.lpb.esb.service.query.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.query.model.EsbRequestDTO;

public interface BaseQueryServiceV3 {
    ResponseModel search(EsbRequestDTO data);
}
