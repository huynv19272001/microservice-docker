package com.lpb.esb.service.transaction.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.transaction.model.EsbRequestDTO;

public interface TransactionService extends BaseTransactionService {
    ResponseModel billV2(EsbRequestDTO data);
}
