package com.esb.card.service;

import com.esb.card.dto.getlistdpp.GetListDPPREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface ListDPPService {
    ResponseModel getListDPP(GetListDPPREQDTO getListDPPREQ);
}
