package com.esb.transaction.service;


import com.esb.transaction.model.EsbServiceProcess;

import java.util.List;

public interface IEsbServiceProcessService {
    List<EsbServiceProcess> getServiceInfo(String serviceId, String productCode);
}
