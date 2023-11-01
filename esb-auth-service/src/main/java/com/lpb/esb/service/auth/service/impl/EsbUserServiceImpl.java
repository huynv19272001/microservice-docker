package com.lpb.esb.service.auth.service.impl;

import com.lpb.esb.service.auth.model.oracle.EsbUser;
import com.lpb.esb.service.auth.repositories.oracle.EsbUserRepository;
import com.lpb.esb.service.auth.service.EsbUserService;
import com.lpb.esb.service.common.utils.rsa.RSAConstants;
import com.lpb.esb.service.common.utils.rsa.RSAUtil;
import com.lpb.esb.service.common.utils.rsa.SHA1Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-13
 */
@Service
public class EsbUserServiceImpl implements EsbUserService {
    @Autowired
    EsbUserRepository esbUserRepository;

    @Override
    public EsbUser findUserByUserName(String username) {
        EsbUser esbUser = esbUserRepository.findById(username).orElse(null);
        return esbUser;
    }

    @Override
    public boolean verifyUser(String username, String passwordInput) {
        EsbUser esbUser = findUserByUserName(username);
        if (esbUser == null) {
            return false;
        }
        String passwordPlaintext = RSAUtil.decryptData(passwordInput, esbUser.getKeyRSA());
        if (passwordPlaintext.equals(RSAConstants.DECRYPT_ERROR)) {
            return false;
        }
        String passSHA1 = SHA1Utils.encryptSHA1(passwordPlaintext);
        // check pass in database
        if (passSHA1.equals(esbUser.getPassword())) {
            return true;
        }
        return false;
    }
}
