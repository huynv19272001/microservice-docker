package com.lpb.esb.service.tct.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.tct.model.request.dto.EsbTctRequestDTO;

/**
 * Created by cuongnm10 on 2022-06-16
 */

public interface MstService {
    ResponseModel search(EsbTctRequestDTO requestData) throws Exception;
}
