package com.lpb.napas.ecom.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.napas.ecom.dto.PurchaseRequest;
import com.lpb.napas.ecom.dto.PurchaseResponse;

public interface IPurchaseProcess {
    PurchaseResponse initPurchaseResponse(PurchaseRequest purchaseRequest) throws JsonProcessingException;
}
