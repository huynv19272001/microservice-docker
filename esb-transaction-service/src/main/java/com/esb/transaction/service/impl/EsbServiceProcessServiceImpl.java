package com.esb.transaction.service.impl;

import com.esb.transaction.respository.EsbServiceProcessRepository;
import com.esb.transaction.service.IEsbServiceProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esb.transaction.model.EsbServiceProcess;

import java.util.List;

@Service
public class EsbServiceProcessServiceImpl implements IEsbServiceProcessService {
    @Autowired
    private EsbServiceProcessRepository esbServiceProcessRepository;

    @Override
    public List<EsbServiceProcess> getServiceInfo(String serviceId, String productCode) {
        return esbServiceProcessRepository.getServiceInfo(serviceId, productCode);
    }
}
