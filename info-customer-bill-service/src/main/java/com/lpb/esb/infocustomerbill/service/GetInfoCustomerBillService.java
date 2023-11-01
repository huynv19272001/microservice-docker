package com.lpb.esb.infocustomerbill.service;

import com.lpb.esb.service.common.model.request.infocustomerbill.DataQueryDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

@SuppressWarnings("rawtypes")
public interface GetInfoCustomerBillService {
    ResponseModel excuteGetInfoCustomerBill(DataQueryDTO Req_DTO);
}
