package com.lpb.esb.settle.service.impl;

import com.lpb.esb.settle.respository.GetTranIDServiceRes;
import com.lpb.esb.settle.service.GetTranIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GetTranIDServiceImpl implements GetTranIDService {
    @Autowired
    GetTranIDServiceRes getTranIDServiceRes;
    @Override
    public String getTranId() {
        return getTranIDServiceRes.getTranId();
    }
}
