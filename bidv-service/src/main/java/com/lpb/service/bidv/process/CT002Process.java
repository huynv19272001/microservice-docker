package com.lpb.service.bidv.process;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.bidv.model.request.LPBDataRequestCT002;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT002;

import java.util.List;

public interface CT002Process {
    ResponseModel<List<BIDVDataResponseCT002>> requestCT002(LPBRequest<LPBDataRequestCT002> lpbRequestCT002) throws Exception;
}
