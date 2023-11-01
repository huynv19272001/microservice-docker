package com.lpb.service.bidv.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.bidv.model.request.LPBDataRequestCT003;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT003;

import java.util.List;

public interface CT003Process {
    ResponseModel<List<BIDVDataResponseCT003>> requestCT003(LPBRequest<LPBDataRequestCT003> lpbRequestCT003) throws Exception;
}
