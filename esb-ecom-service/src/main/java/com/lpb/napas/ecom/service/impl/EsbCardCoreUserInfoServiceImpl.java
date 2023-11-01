package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.repository.IEsbCardCoreUserInfoRepository;
import com.lpb.napas.ecom.service.EsbCardCoreUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbCardCoreUserInfoServiceImpl implements EsbCardCoreUserInfoService {
    @Autowired
    IEsbCardCoreUserInfoRepository esbCardCoreUserInfoRepository;

    @Override
    public EsbCardCoreUserInfo getEsbCardCoreUserInfoByUsername(String username) {
        return esbCardCoreUserInfoRepository.getEsbCardCoreUserInfoByUsername(username);
    }
}
