package com.lpb.esb.service.sms.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.request.TelcoRequest;

public interface TelcoService {
    ResponseModel updateTelco(TelcoRequest telcoRequest);
}
