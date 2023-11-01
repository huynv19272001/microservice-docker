package com.esb.card.service;

import com.esb.card.dto.getlisttrans.GetListTransREQDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface GetListTransService {
    ResponseModel getListTrans(GetListTransREQDTO getListTransREQDTO);
}
