package com.esb.card.service;

import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.model.EsbServiceProcess;

import java.util.List;


public interface IEsbService {
    List<ServiceInfoDTO> getServiceInfo(String serviceId, String productCode, String hasRole);

    EsbCardCoreUserInfo getCardCoreUser(String userId);

    List<EsbServiceProcess> getServiceInfo(String serviceId, String productCode);
}
