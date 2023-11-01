package com.lpb.esb.service.tct.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.tct.model.request.*;
/**
 * Created by tudv1 on 2022-02-24
 */
public interface BaseTctService {
    ResponseModel search(RequestData requestData) throws Exception;
}
