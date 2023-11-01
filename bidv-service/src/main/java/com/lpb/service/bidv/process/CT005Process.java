package com.lpb.service.bidv.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.bidv.model.request.LPBDataRequestCT005;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT005;

import java.util.List;

public interface CT005Process {
    ResponseModel<List<BIDVDataResponseCT005>> requestCT005(LPBRequest<LPBDataRequestCT005> lpbRequestCT005) throws Exception;
}
