package com.lpb.esb.service.tct.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.model.request.dto.EsbTctRequestDTO;

/**
 * Created by tudv1 on 2022-03-02
 */
public interface ChungTuService {
     ResponseModel sendData(RequestData requestData);
     ResponseModel huyChungTu(RequestData requestData);

    ResponseModel truyvan(EsbTctRequestDTO requestData) throws Exception;
}
