package com.lpb.esb.settle.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.request.settle.DataSettleDTO;

public interface SettleService {
    ResponseModel excuteSettleService(DataSettleDTO req);
}
