package com.lpb.service.bidv.service;

import com.lpb.service.bidv.model.entities.EsbBIDVTransaction;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;

import java.util.List;

public interface EsbBIDVTransactionService {
    void save(EsbBIDVTransaction esbBIDVTransaction);

    void saveAll(List<EsbBIDVTransaction> listEsbBIDVTransaction);

    String getAppMsgID();

    List<EsbBIDVTransactionRepository.ServiceInfo> listServiceInfo(String hasRole, String serviceId, String productCode);
}
