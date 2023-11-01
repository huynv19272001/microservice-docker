package com.lpb.esb.settle.service;

import com.lpb.esb.service.common.model.request.settle.DataSettleDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface RevertService {
    ResponseModel excuteRevertService(DataSettleDTO req);
}
