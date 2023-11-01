package com.lpb.esb.service.transaction.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.transaction.model.EsbRequestDTO;

public interface BaseTransactionService {
    ResponseModel payment(EsbRequestDTO data);

    ResponseModel bill(EsbRequestDTO data);

    ResponseModel revert(EsbRequestDTO data);
}
