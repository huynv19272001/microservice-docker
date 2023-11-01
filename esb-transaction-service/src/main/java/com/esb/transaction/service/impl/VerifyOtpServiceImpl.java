package com.esb.transaction.service.impl;

import com.esb.transaction.configuration.OtpConfig;
import com.esb.transaction.dto.CreateOtpREQDTO;
import com.esb.transaction.dto.VerifyOtpREQDTO;
import com.esb.transaction.respository.IVerifyOtpDAO;
import com.esb.transaction.service.IVerifyOtpService;
import com.esb.transaction.utils.MD5Utils;
import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class VerifyOtpServiceImpl implements IVerifyOtpService {
    @Autowired
    private IVerifyOtpDAO verifyOtpDAO;

    @Autowired
    private OtpConfig otpConfig;

    @Override
    public ResponseModel createOtp(CreateOtpREQDTO createOtpREQ) {
        String otpCode = generateOTP(Integer.parseInt(otpConfig.getLength()));
        String encOtp = MD5Utils.encryptMD5(otpCode);
        log.info(createOtpREQ.getAppMsgId() + " OTP: " + otpCode);
        ResponseModel responseModel = verifyOtpDAO.verifyOtp(createOtpREQ.getAppMsgId(),
            createOtpREQ.getUserId(), encOtp, 0);
        responseModel.setData(otpCode);
        return responseModel;
    }

    @Override
    public ResponseModel verifyOtp(VerifyOtpREQDTO verifyOtpREQ) {
        String encOtp = MD5Utils.encryptMD5(verifyOtpREQ.getOtpCode());
        ResponseModel responseModel = verifyOtpDAO.verifyOtp(verifyOtpREQ.getAppMsgId(),
            verifyOtpREQ.getUserId(), encOtp, 1);
        return responseModel;
    }

    public String generateOTP(int lengthOTP) {
        return new String(OTP(lengthOTP));
    }

    public char[] OTP(int len) {
        // Using numeric values
        String numbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Using random method
        Random rndm_method = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
}
