package com.esb.card.service;

import com.esb.card.dto.availinfocard.AvailInfoCardREQDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoREQDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface ICardService {
    ResponseModel getCardInfo(CardInfoREQDTO cardInfoRequest);
    ResponseModel getDebitCardInfo(DebitCardInfoREQDTO debitCardInfoREQDTO);
    ResponseModel getListAcc(DebitCardInfoREQDTO debitCardInfoREQDTO);
    ResponseModel getAvailInfoCard(AvailInfoCardREQDTO availInfoCardREQDTO);
    ResponseModel updateLimitPayment(UpdateLimitPaymentREQDTO updateLimitPaymentREQDTO);
}
