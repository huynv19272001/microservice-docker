package com.lpb.esb.service.query.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.query.model.queryV2.EsbReqDTO;

public interface QueryServiceV2 {
    ResponseModel search(EsbReqDTO data);
}
