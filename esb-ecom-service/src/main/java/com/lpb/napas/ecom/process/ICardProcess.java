package com.lpb.napas.ecom.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.CardInfoREQDTO;
import com.lpb.napas.ecom.dto.DebitCardInfoREQDTO;

public interface ICardProcess {
    ResponseModel executeListCardInfo(CardInfoREQDTO cardInfoREQDTO, String requestorTransId);

    ResponseModel executeDebitCardInfo(DebitCardInfoREQDTO debitCardInfoREQDTO, String requestorTransId);
}
