package com.esb.card.service;

import com.esb.card.dto.checklatedatcard.CheckLateDateCardByCifREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface CheckLateDateCardByCifService {
    ResponseModel checkLateDateCardByCif(CheckLateDateCardByCifREQDTO checkLateDateCardByCifREQDTO);
}
