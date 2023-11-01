package com.lpb.esb.service.lv24.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.lv24.model.EsbRequestDTO;

public interface QueryHrAndCardCifService {
    ResponseModel callTwoApis(EsbRequestDTO esbRequest);
}
