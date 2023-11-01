package com.lpb.service.mafc.service;

import com.lpb.service.mafc.model.MAFCRequest;
import com.lpb.service.mafc.model.MAFCResponse;
import com.lpb.service.mafc.model.MAFCSettleBillRequest;
import com.lpb.service.mafc.model.MAFCSettleBillResponse;

public interface RequestMAFCService {
    MAFCResponse getLoanInfor(MAFCRequest req);

    MAFCSettleBillResponse payBillMafc(MAFCSettleBillRequest req);

}
