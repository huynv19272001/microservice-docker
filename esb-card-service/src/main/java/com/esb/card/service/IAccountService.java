package com.esb.card.service;

import com.esb.card.dto.getaccountlist.GetAccountListREQDTO;
import com.esb.card.dto.getavlbalance.GetAvlBalanceREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.w3c.dom.Document;

public interface IAccountService {
    ResponseModel getDataAccountList(Document doc) throws Exception;

    ResponseModel getDataAvlBalance(Document doc) throws Exception;

    ResponseModel getAccountList(GetAccountListREQDTO getAccountListDTO);

    ResponseModel getAvlBalance(GetAvlBalanceREQDTO getAvlBalanceDTO);
}
