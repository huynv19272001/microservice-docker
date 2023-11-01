package com.lpb.insurance.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.insurance.model.EsbRequestDTO;

public interface CategoryService extends BaseService {
    ResponseModel category(EsbRequestDTO data);
}
