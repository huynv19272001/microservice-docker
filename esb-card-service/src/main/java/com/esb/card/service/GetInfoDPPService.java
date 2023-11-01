package com.esb.card.service;

import com.esb.card.dto.getinfodpp.GetInfoDPPREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface GetInfoDPPService {
    ResponseModel getInfoDPP(GetInfoDPPREQDTO getInfoDPPREQDTO);
}
