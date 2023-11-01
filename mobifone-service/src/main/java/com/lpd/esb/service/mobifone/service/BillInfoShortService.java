package com.lpd.esb.service.mobifone.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpd.esb.service.mobifone.model.Request;

public interface BillInfoShortService extends BaseService {
    ResponseModel getInfoShort(Request request);
}
