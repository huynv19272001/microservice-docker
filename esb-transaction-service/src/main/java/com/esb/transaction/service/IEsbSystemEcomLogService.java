package com.esb.transaction.service;

import com.esb.transaction.model.EsbSystemEcomLog;

public interface IEsbSystemEcomLogService {
    void save(EsbSystemEcomLog esbSystemEcomLog);

    String getAppMsgID();
}
