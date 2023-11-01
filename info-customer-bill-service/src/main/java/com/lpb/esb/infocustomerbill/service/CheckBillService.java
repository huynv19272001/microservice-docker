package com.lpb.esb.infocustomerbill.service;

import com.lpb.esb.service.common.model.request.infocustomerbill.DataQueryDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

@SuppressWarnings("rawtypes")
public interface CheckBillService {
    ResponseModel excuteCheckBill(DataQueryDTO Req_DTO);
}
