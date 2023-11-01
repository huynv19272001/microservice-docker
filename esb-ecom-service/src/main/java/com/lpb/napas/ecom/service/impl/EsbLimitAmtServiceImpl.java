package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.model.EsbLimitAmt;
import com.lpb.napas.ecom.repository.IEsbLimitAmtRepository;
import com.lpb.napas.ecom.service.EsbLimitAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbLimitAmtServiceImpl implements EsbLimitAmtService {
    @Autowired
    IEsbLimitAmtRepository esbLimitAmtRepository;

    @Override
    public List<EsbLimitAmt> getListByServiceId(String serviceId) {
        return esbLimitAmtRepository.getListByServiceId(serviceId);
    }
}
