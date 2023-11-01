package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.config.repository.EsbTransactionBillingRepository;
import com.lpb.esb.service.config.service.EsbTransactionBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbTransactionBillingServiceImpl implements EsbTransactionBillingService {
    @Autowired
    EsbTransactionBillingRepository esbTransactionBillingRepository;

    @Override
    public void updateEsbTransactionBilling(String appMsgId, String serviceId) {
        esbTransactionBillingRepository.updateEsbTransactionBilling(appMsgId, serviceId);
    }
}
