package com.esb.card.service;

import com.esb.card.dto.creditcardinfo.CreditCardInfoREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface CreditCardInfoService {
    ResponseModel getCreditCard(CreditCardInfoREQDTO creditCardInfoREQDTO);
}
