package com.lpb.insurance.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.insurance.model.EsbRequestDTO;

public interface FeeService extends BaseService {
    ResponseModel feePrivateInsurance(EsbRequestDTO data);

    ResponseModel feeCreditInsurance(EsbRequestDTO data);

    ResponseModel feeBaoAnCredit(EsbRequestDTO data);

    ResponseModel feeCarsInsurance(EsbRequestDTO data);
}

