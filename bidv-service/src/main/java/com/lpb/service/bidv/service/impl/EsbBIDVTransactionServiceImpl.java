package com.lpb.service.bidv.service.impl;

import com.lpb.service.bidv.model.entities.EsbBIDVTransaction;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;
import com.lpb.service.bidv.service.EsbBIDVTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EsbBIDVTransactionServiceImpl implements EsbBIDVTransactionService {
    @Autowired
    EsbBIDVTransactionRepository esbBIDVTransactionRepository;


    @Override
    @Transactional
    public void save(EsbBIDVTransaction esbBIDVTransaction) {
        esbBIDVTransactionRepository.save(esbBIDVTransaction);
    }

    @Override
    @Transactional
    public void saveAll(List<EsbBIDVTransaction> listEsbBIDVTransaction) {
        esbBIDVTransactionRepository.saveAll(listEsbBIDVTransaction);
    }

    @Override
    public String getAppMsgID() {
        return String.valueOf(esbBIDVTransactionRepository.getAppMsgID());
    }

    @Override
    public List<EsbBIDVTransactionRepository.ServiceInfo> listServiceInfo(String hasRole, String serviceId, String productCode) {
        return esbBIDVTransactionRepository.listServiceInfo(hasRole, serviceId, productCode);
    }
}
