package com.lpb.service.bidv.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.bidv.model.request.LPBDataRequestCT006;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT006;

import java.util.List;

public interface CT006Process {
    ResponseModel<List<BIDVDataResponseCT006>> requestCT006(LPBRequest<LPBDataRequestCT006> lpbRequestCT006) throws Exception;
}
