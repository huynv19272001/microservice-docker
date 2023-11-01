package com.esb.card.service;

import com.esb.card.dto.availinfocard.AvailInfoCardREQDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoREQDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.unif.customerinfo.UpdateCustomerInfoREQDTO;
import com.esb.card.dto.unif.updatecardstatus.UpdateCardStatusREQDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface ICardUNIFService {
    ResponseModel updateCustomerInfo(UpdateCustomerInfoREQDTO requestDTO);

    ResponseModel updateCardStatus(UpdateCardStatusREQDTO requestDTO);

    ResponseModel linkAccount(CardInfoREQDTO requestDTO);

    ResponseModel changeDefaultAccount(CardInfoREQDTO requestDTO);
}
