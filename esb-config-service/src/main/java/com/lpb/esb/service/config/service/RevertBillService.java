package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface RevertBillService {
    ResponseModel initRevertBill(BaseRequestDTO baseRequestDTO);

    ResponseModel getRevertBill(BaseRequestDTO baseRequestDTO);
}

