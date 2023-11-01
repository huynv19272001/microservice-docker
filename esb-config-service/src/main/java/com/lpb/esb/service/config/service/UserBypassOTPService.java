package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.config.model.dto.OtpDTO;

/**
 * Created by cuongnm10 on 2022-06-29
 */
public interface UserBypassOTPService {

    //list all user
    ResponseModel getAllUser(int pageNumber, int pageSize);

    // detail user
    ResponseModel getDetailUser(String userId);


    // insert
    ResponseModel save(OtpDTO otpDTO);

    // update
    void updateUser(OtpDTO otpDTOEntity, String checkerName);

    // update
}
