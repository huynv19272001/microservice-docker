package com.lpb.napas.ecom.service;

import com.lpb.napas.ecom.model.EsbSystemEcomLog;

import java.util.List;

public interface IEsbSystemEcomLogService {
    void save(EsbSystemEcomLog esbSystemEcomLog);

    String getAppMsgID();

    EsbSystemEcomLog checkExitsTransaction (String requestorTransId, String methodAction);

    List<EsbSystemEcomLog> getListEsbSystemEcomLog(String requestorTransId, String methodAction);

}
