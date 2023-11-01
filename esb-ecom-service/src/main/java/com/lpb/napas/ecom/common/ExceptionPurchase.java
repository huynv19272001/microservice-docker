package com.lpb.napas.ecom.common;

import com.lpb.napas.ecom.dto.DataPurchaseRequest;
import com.lpb.napas.ecom.dto.InitPurchaseRequest;
import com.lpb.napas.ecom.dto.PurchaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionPurchase extends RuntimeException {
    PurchaseRequest purchaseRequest;
    InitPurchaseRequest initPurchaseRequest;
    DataPurchaseRequest dataPurchaseRequest;
    String appId;
}
