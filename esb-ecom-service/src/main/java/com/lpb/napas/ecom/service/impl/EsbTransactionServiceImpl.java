package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.repository.IEsbTransactionRepository;
import com.lpb.napas.ecom.service.EsbTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbTransactionServiceImpl implements EsbTransactionService {
    @Autowired
    IEsbTransactionRepository esbTransactionRepository;

    @Override
    public void updateTransaction(String requestRefNo, String appMsgId) {
        esbTransactionRepository.updateEsbTransaction(requestRefNo, appMsgId);
    }
}
