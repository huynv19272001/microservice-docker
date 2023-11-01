package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface TransactionService {
    ResponseModel initTransaction(BaseRequestDTO baseRequestDTO);

    ResponseModel getTransactionPost(BaseRequestDTO baseRequestDTO);

    ResponseModel updateTransaction(BaseRequestDTO baseRequestDTO);
}
