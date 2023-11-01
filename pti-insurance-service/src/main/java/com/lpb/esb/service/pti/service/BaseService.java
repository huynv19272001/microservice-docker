package com.lpb.esb.service.pti.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;

public interface BaseService {
    ResponseModel khoiDong(EsbRequestDTO data);

}
