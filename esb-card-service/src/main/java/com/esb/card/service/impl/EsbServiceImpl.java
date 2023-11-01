package com.esb.card.service.impl;

import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.model.EsbServiceProcess;
import com.esb.card.respository.EsbCardCoreUserInfoRepository;
import com.esb.card.respository.EsbPermissionRepository;
import com.esb.card.respository.EsbServiceProcessRepository;
import com.esb.card.service.IEsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbServiceImpl implements IEsbService {
    @Autowired
    private EsbPermissionRepository esbPermissionRepository;

    @Autowired
    private EsbCardCoreUserInfoRepository coreUserInfoRepository;

    @Autowired
    private EsbServiceProcessRepository esbServiceProcessRepository;

    @Override
    public List<ServiceInfoDTO> getServiceInfo(String serviceId, String productCode, String hasRole) {
        return esbPermissionRepository.getServiceInfo(serviceId, productCode, hasRole);
    }

    @Override
    public EsbCardCoreUserInfo getCardCoreUser(String userId) {
        return coreUserInfoRepository.findById(userId).get();
    }

    @Override
    public List<EsbServiceProcess> getServiceInfo(String serviceId, String productCode) {
        return esbServiceProcessRepository.getServiceInfo(serviceId, productCode);
    }
}
