package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface SettleBillService {
    ResponseModel initSettleBill(BaseRequestDTO baseRequestDTO);

    ResponseModel getSettleBill(BaseRequestDTO baseRequestDTO);

    ResponseModel updateSettleBill(BaseRequestDTO baseRequestDTO);

    ResponseModel billingLog(BaseRequestDTO baseRequestDTO);
}

