package com.lpb.napas.ecom.service;

import com.lpb.napas.ecom.model.EsbLimitAmt;

import java.util.List;

public interface EsbLimitAmtService {
    List<EsbLimitAmt> getListByServiceId(String serviceId);
}
