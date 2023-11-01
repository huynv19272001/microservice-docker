package com.lpb.napas.ecom.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.CardInfoDTO;
import com.lpb.napas.ecom.dto.GetAccountListDTO;
import com.lpb.napas.ecom.dto.GetAccountListREQDTO;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;

public interface IGetListAccountProcess {
    ResponseModel excuteGetListAccount(GetAccountListDTO getAccountListDTO, String requestorTransId, String appId);

    GetAccountListREQDTO initGetAccountListDTO(CardInfoDTO cardInfoDTO, EsbSystemEcomLog esbSystemEcomLog);
}
