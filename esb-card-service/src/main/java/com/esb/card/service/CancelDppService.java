package com.esb.card.service;

import com.esb.card.dto.canceldpp.CancelDppREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface CancelDppService {
    ResponseModel cancelDpp(CancelDppREQDTO cancelDppREQDTO);
}
