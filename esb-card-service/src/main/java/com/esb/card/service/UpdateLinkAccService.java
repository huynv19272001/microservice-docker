package com.esb.card.service;

import com.esb.card.dto.updatelinkacc.UpdateLinkAccREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface UpdateLinkAccService {
    ResponseModel updateLinkAcc(UpdateLinkAccREQDTO updateLinkAccREQDTO);
}
