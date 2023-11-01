package com.lpb.napas.ecom.repository.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.dto.InitValidAmtRequest;
import com.lpb.napas.ecom.model.config.PurchaseConfigApi;
import com.lpb.napas.ecom.repository.ValidAmtDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Log4j2
@Repository
public class ValidAmtDAOImpl implements ValidAmtDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PurchaseConfigApi purchaseConfigApi;

    @Override
    public ResponseModel validAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        SimpleJdbcCall jdbcCall;

        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        BigDecimal result = BigDecimal.valueOf(0);
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_LIMIT)
                .withFunctionName(Constant.FnValidAmtOneDay)
                .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_Amt", initValidAmtRequest.getAmt())
                .addValue("p_minAmt", initValidAmtRequest.getMinAmt())
                .addValue("p_maxAmt", initValidAmtRequest.getMaxAmt())
                .addValue("p_serviceId", initValidAmtRequest.getServiceId())
                .addValue("p_customerNo", initValidAmtRequest.getCustomerNo());
            result = jdbcCall.executeFunction(BigDecimal.class, paramMap);
            if (result.intValue() == 1) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + appId + " FnValidAmtOneDay: " + lpbResCode.getErrorDesc());
        } catch (Exception e) {
            log.info(requestorTransId + "-" + appId + " Exception FnValidAmtOneDay: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel validCountAmtOneDay(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        SimpleJdbcCall jdbcCall;
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        BigDecimal result = BigDecimal.valueOf(0);
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_LIMIT)
                .withFunctionName(Constant.FnValidCountAmtOneDay)
                .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_countPurchase", Integer.valueOf(purchaseConfigApi.getCountPurchase()))
                .addValue("p_serviceId", initValidAmtRequest.getServiceId())
                .addValue("p_customerNo", initValidAmtRequest.getCustomerNo());
            result = jdbcCall.executeFunction(BigDecimal.class, paramMap);
            if (result.intValue() == 1) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }

            log.info(requestorTransId + "-" + appId + " FnValidCountAmtOneDay: " + lpbResCode.getErrorDesc());
        } catch (Exception e) {
            log.info(requestorTransId + "-" + appId + " Exception FnValidCountAmtOneDay: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel validAmtOneTxn(InitValidAmtRequest initValidAmtRequest, String requestorTransId, String appId) {
        SimpleJdbcCall jdbcCall;
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        BigDecimal result = BigDecimal.valueOf(0);
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_LIMIT)
                .withFunctionName(Constant.FnValidAmtOneTxn)
                .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_Amt", initValidAmtRequest.getAmt())
                .addValue("p_minAmt", initValidAmtRequest.getMinAmt())
                .addValue("p_maxAmt", initValidAmtRequest.getMaxAmt())
                .addValue("p_serviceId", initValidAmtRequest.getServiceId())
                .addValue("p_customerNo", initValidAmtRequest.getCustomerNo());
            result = jdbcCall.executeFunction(BigDecimal.class, paramMap);
            if (result.intValue() == 1) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + appId + " FnValidAmtOneTxn: " + lpbResCode.getErrorDesc());
        } catch (Exception e) {
            log.info(requestorTransId + "-" + appId + " Exception FnValidAmtOneTxn: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }
}
