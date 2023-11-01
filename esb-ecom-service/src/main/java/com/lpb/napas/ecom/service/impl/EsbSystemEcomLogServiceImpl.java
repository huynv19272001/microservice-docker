package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.repository.IEsbSystemEcomLogRepository;
import com.lpb.napas.ecom.service.IEsbSystemEcomLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbSystemEcomLogServiceImpl implements IEsbSystemEcomLogService {
    @Autowired
    IEsbSystemEcomLogRepository esbSystemEcomLogRepository;

    @Override
    public void save(EsbSystemEcomLog esbSystemEcomLog) {
        esbSystemEcomLogRepository.save(esbSystemEcomLog);
    }

    @Override
    public String getAppMsgID() {
        return String.valueOf(esbSystemEcomLogRepository.getAppMsgID());
    }

    @Override
    public EsbSystemEcomLog checkExitsTransaction(String requestorTransId, String methodAction) {
        return esbSystemEcomLogRepository.checkExitsTransaction(requestorTransId, methodAction);
    }

    @Override
    public List<EsbSystemEcomLog> getListEsbSystemEcomLog(String requestorTransId, String methodAction) {
        return esbSystemEcomLogRepository.getListEsbSystemEcomLog(requestorTransId, methodAction);
    }
}
