package com.esb.card.service;

import com.esb.card.dto.cardtranlimit.GetCardTranLimitREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface GetCardTranLimitService {
    ResponseModel getCardTranLimit(GetCardTranLimitREQDTO getCardTranLimitREQDTO);
}
