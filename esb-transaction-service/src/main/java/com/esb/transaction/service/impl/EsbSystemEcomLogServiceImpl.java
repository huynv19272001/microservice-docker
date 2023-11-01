package com.esb.transaction.service.impl;

import com.esb.transaction.model.EsbSystemEcomLog;
import com.esb.transaction.respository.IEsbSystemEcomLogRepository;
import com.esb.transaction.service.IEsbSystemEcomLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
