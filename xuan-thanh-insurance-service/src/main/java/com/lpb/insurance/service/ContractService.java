package com.lpb.insurance.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.insurance.model.EsbRequestDTO;

public interface ContractService extends BaseService{
    ResponseModel list(EsbRequestDTO data);
    ResponseModel cancel(EsbRequestDTO data);
    ResponseModel status(EsbRequestDTO data);
}
