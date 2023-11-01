package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.model.EsbService;
import com.lpb.napas.ecom.repository.IEsbServiceRepository;
import com.lpb.napas.ecom.service.IEsbServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EsbServiceServiceImpl implements IEsbServiceService {
    @Autowired
    private IEsbServiceRepository esbServiceRepository;

    @Override
    public EsbService getEsbServiceByServiceId(String serviceId) {
        return esbServiceRepository.getEsbServiceByServiceId(serviceId);
    }
}
