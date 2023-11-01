package com.esb.card.service;

import com.esb.card.dto.telcoRequest.EsbTelCoReqDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface TelcoService {
    ResponseModel updateTelco(EsbTelCoReqDTO telcoRequest);

}
