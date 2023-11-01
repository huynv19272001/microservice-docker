package com.lpb.esb.service.auth.service;

import com.lpb.esb.service.auth.model.oracle.EsbUser;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-13
 */
@Service
public interface EsbUserService {
    EsbUser findUserByUserName(String username);

    boolean verifyUser(String username, String passwordInput);
}
