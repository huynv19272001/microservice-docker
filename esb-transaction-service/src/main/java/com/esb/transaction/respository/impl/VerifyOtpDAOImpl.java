package com.esb.transaction.respository.impl;

import com.esb.transaction.constants.Constant;
import com.esb.transaction.respository.IVerifyOtpDAO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Log4j2
@Repository
public class VerifyOtpDAOImpl implements IVerifyOtpDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResponseModel verifyOtp(String appMsgId, String userId, String otpCode,
                                   int initOrAuthOtp) {
        SimpleJdbcCall jdbcCall;
        String result = "N";
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_INIT_TRANSACTION)
                .withFunctionName(Constant.Fn_OTP)
                .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_userID", userId)
                .addValue("p_otpcode", otpCode)
                .addValue("p_AppMsgID", appMsgId)
                .addValue("p_initOrAuth", initOrAuthOtp)
                .addValue("p_ErrorCode", null)
                .addValue("p_ErrorDesc", null);
            Map results = jdbcCall.execute(paramMap);
            result = (String) results.get("return");
            String errorCode = (String) results.get("P_ERRORCODE");

            if (errorCode.equals("ESB-000")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else if (errorCode.equals("ESB-042")) {
                lpbResCode.setErrorCode(ErrorMessage.WRONG_OTP.label);
                lpbResCode.setErrorDesc(ErrorMessage.WRONG_OTP.description);
            } else if (errorCode.equals("ESB-041")) {
                lpbResCode.setErrorCode(ErrorMessage.EXPIRED_OTP.label);
                lpbResCode.setErrorDesc(ErrorMessage.EXPIRED_OTP.description);
            } else if (errorCode.equals("ESB-043")) {
                lpbResCode.setErrorCode(ErrorMessage.WRONG_MANY_OTP.label);
                lpbResCode.setErrorDesc(ErrorMessage.WRONG_MANY_OTP.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(appMsgId + " Verify or create OTP: " + lpbResCode.getErrorDesc());
            responseModel.setResCode(lpbResCode);
            return responseModel;
        } catch (Exception e) {
            log.info(appMsgId + " Exception authenOTP: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }
}
