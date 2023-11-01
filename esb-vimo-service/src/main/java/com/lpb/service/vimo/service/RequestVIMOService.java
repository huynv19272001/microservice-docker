package com.lpb.service.vimo.service;

import com.lpb.service.vimo.model.*;

public interface RequestVIMOService {
    ESBResponse querybill(ESBRequest req);

    ESBSettleBillResponse paybillv2(ESBSettleBillRequest req);

    ESBSettleBillResponse paybillpartial(ESBSettleBillRequest req);

    ESBResponse getaddfee(ESBRequest req);


}
