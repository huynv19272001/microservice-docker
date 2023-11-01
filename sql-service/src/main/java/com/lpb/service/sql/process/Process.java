package com.lpb.service.sql.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.RequestDTO;

public interface Process {
    ResponseModel executeProcess(RequestDTO requestDTO) throws JsonProcessingException;
}
