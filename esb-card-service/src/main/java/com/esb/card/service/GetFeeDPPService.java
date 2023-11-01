package com.esb.card.service;

import com.esb.card.dto.getfeedpp.GetFeeDPPREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface GetFeeDPPService {
    ResponseModel getFeeDPP(GetFeeDPPREQDTO getFeeDPPREQDTO);
}
