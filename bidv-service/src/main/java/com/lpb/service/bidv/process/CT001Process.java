package com.lpb.service.bidv.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.bidv.model.request.LPBDataRequestCT001;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT001;

import java.util.List;

public interface CT001Process {
    ResponseModel<List<BIDVDataResponseCT001>> requestCT001(LPBRequest<LPBDataRequestCT001> lpbRequestCT001) throws Exception;
}
