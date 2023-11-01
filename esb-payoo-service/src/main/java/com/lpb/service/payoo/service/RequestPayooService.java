package com.lpb.service.payoo.service;

import com.lpb.service.payoo.model.*;

public interface RequestPayooService {
    ESBResponse queryBillBE(ESBRequest req) throws Throwable;

    ESBSettleBillResponse payOnlineBillEx(ESBSettleBillRequest req) throws Throwable;

    ESBQueryTransResponse getTransactionStatusBE(ESBQueryTransRequest req) throws Throwable;
}

