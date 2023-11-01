package com.esb.transaction.service;

import com.esb.transaction.dto.ServiceInfoDTO;
import com.esb.transaction.model.EsbCardCoreUserInfo;

import java.util.List;


public interface IEsbService {
    List<ServiceInfoDTO> getServiceInfo(String serviceId, String productCode, String hasRole);

    EsbCardCoreUserInfo getCardCoreUser(String userId);
}
