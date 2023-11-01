package com.esb.transaction.service.impl;

import com.esb.transaction.dto.ServiceInfoDTO;
import com.esb.transaction.model.EsbCardCoreUserInfo;
import com.esb.transaction.respository.EsbCardCoreUserInfoRepository;
import com.esb.transaction.respository.EsbPermissionRepository;
import com.esb.transaction.service.IEsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbServiceImpl implements IEsbService {
    @Autowired
    private EsbPermissionRepository esbPermissionRepository;

    @Autowired
    private EsbCardCoreUserInfoRepository coreUserInfoRepository;

    @Override
    public List<ServiceInfoDTO> getServiceInfo(String serviceId, String productCode, String hasRole) {
        return esbPermissionRepository.getServiceInfo(serviceId, productCode, hasRole);
    }

    @Override
    public EsbCardCoreUserInfo getCardCoreUser(String userId) {
        return coreUserInfoRepository.findById(userId).get();
    }
}
