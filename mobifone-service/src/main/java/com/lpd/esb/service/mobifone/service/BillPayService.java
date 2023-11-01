package com.lpd.esb.service.mobifone.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpd.esb.service.mobifone.model.Request;

public interface BillPayService extends BaseService {
    ResponseModel prepaid(Request request);

    ResponseModel postpaid(Request request);

    ResponseModel cancel(Request request);
}
