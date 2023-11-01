package com.lpb.napas.ecom.process;

import com.lpb.napas.ecom.dto.DataPurchaseRequest;
import com.lpb.napas.ecom.dto.InitPurchaseRequest;
import com.lpb.napas.ecom.dto.PurchaseRequest;
import com.lpb.napas.ecom.dto.PurchaseResponse;

public interface IInitPurchaseResponse {
    PurchaseResponse initPurchaseResponse(PurchaseRequest purchaseRequest,
                                          InitPurchaseRequest initPurchaseRequest,
                                          DataPurchaseRequest dataPurchaseRequest,
                                          String appId) throws Exception;

    void updateTransaction(InitPurchaseRequest initPurchaseRequest,
                           String appId);
}
