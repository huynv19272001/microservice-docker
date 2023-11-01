package com.lpb.esb.service.config.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.BillingLogDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateSettleBillDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.constant.Constant;
import com.lpb.esb.service.config.repository.SettleBillDAO;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.Types;

@Log4j2
@Repository
public class SettleBillDAOImpl implements SettleBillDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseModel switchingEsb(String userId, String trnBranch, String trnDesc,
                                      String appMsgId, String xmlTypeCustomer,
                                      String confirmReq, String xmlTypePartnerInfo,
                                      String xmlTypeService, String txnCode, BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            Session session = this.entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                CallableStatement function = null;
                try {
                    function = connection.prepareCall(Constant.Fn_Request_Transaction);
                    function.registerOutParameter(1, Types.VARCHAR);
                    function.setString(2, userId);
                    function.setString(3, trnBranch);
                    function.setString(4, trnDesc);
                    function.setString(5, appMsgId);
                    function.setString(6, xmlTypeCustomer);
                    function.setString(7, confirmReq);
                    function.setString(8, xmlTypePartnerInfo);
                    function.setString(9, xmlTypeService);
                    function.setString(10, txnCode);
                    function.registerOutParameter(11, Types.VARCHAR);
                    function.registerOutParameter(12, Types.VARCHAR);
                    log.info(request.getHeader().getMsgId() + "-" + appMsgId + " Start Call Package");

                    function.execute();
                    if (function.getString(1).equals("Y")) {
                        lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                        lpbResCode.setErrorDesc(function.getString(12));
                    } else {
                        lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                        lpbResCode.setErrorDesc(function.getString(12));
                    }
                    log.info(request.getHeader().getMsgId() + "-" + appMsgId + " SwitchingEsb " + "ErrorCode: " +
                        function.getString(11) + " ErrorDesc: " + lpbResCode.getErrorDesc());
                } finally {
                    if (function != null) {
                        function.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info(request.getHeader().getMsgId() + "-" + appMsgId + " Exception SwitchingEsb: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel updateSettleBill(UpdateSettleBillDTO updateSettleBillDTO, String xmlServiceInfo,
                                          String serviceType, BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            Session session = this.entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                CallableStatement function = null;
                try {
                    switch (serviceType) {
                        case "TRANSFER":
                            function = connection.prepareCall(Constant.FUNCTION_LOG_TRANSFER);
                            break;
                        default:
                            function = connection.prepareCall(Constant.FUNCTION_LOG_BILLING);
                            break;
                    }
                    function.registerOutParameter(1, Types.VARCHAR);
                    function.setString(2, updateSettleBillDTO.getTransactionId());
                    function.setString(3, updateSettleBillDTO.getErrorCode());
                    function.setString(4, updateSettleBillDTO.getErrorDesc());
                    function.setString(5, xmlServiceInfo);
                    function.registerOutParameter(2, Types.VARCHAR);
                    function.registerOutParameter(3, Types.VARCHAR);
                    log.info(request.getHeader().getMsgId() + "-" + updateSettleBillDTO.getTransactionId() + " Start Call Package");

                    function.execute();
                    if (function.getString(3).equals("ESB-000")) {
                        lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                        lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                    } else if (function.getString(3).equals("ESB-001")) {
                        lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                        lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                    } else {
                        lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                        lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
                    }

                    log.info(request.getHeader().getMsgId() + "-" + updateSettleBillDTO.getTransactionId() + " UpdateSettleBill  " + "ErrorCode: " +
                        function.getString(3) + " ErrorDesc: " + lpbResCode.getErrorDesc());
                } finally {
                    if (function != null) {
                        function.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info(request.getHeader().getMsgId() + "-" + updateSettleBillDTO.getTransactionId() + " Exception UpdateSettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public void initPayBill(String rowId, String serviceId) {
        SimpleJdbcCall jdbcCall;
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_BILLING)
                .withFunctionName(Constant.INIT_PAYBILL);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_Trn_Srvc_Row_ID", rowId)
                .addValue("p_Service_ID", serviceId);

            log.info(rowId + "-" + serviceId + " Start Call Package");
            String result = jdbcCall.executeFunction(String.class, paramMap);
            log.info(rowId + "-" + serviceId + " initPayBill result: " + result);

        } catch (Exception e) {
            log.info(rowId + "-" + serviceId + " Exception initPayBill: " + e);
        }
    }

    @Override
    public ResponseModel billingLog(BaseRequestDTO baseRequestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BillingLogDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), BillingLogDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + " Billing Log: " + request.getBillingLog());
            log.info(baseRequestDTO.getHeader().getMsgId() + " Billing Log: " + request.getTxnReqEsb());
            log.info(baseRequestDTO.getHeader().getMsgId() + " Billing Log: " + request.getTxnResEsb());
            log.info(baseRequestDTO.getHeader().getMsgId() + " Billing Log: " + request.getTxnReqPar());
            log.info(baseRequestDTO.getHeader().getMsgId() + " Billing Log: " + request.getTxnResPar());
            Session session = this.entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                CallableStatement function = null;
                try {
                    function = connection.prepareCall(Constant.PKG_ESB_BILLING_FN_BILLING_LOG);
                    function.setString(1, request.getBillingLog());
                    function.setString(2, request.getTxnReqEsb());
                    function.setString(3, request.getTxnResEsb());
                    function.setString(4, request.getTxnReqPar());
                    function.setString(5, request.getTxnResPar());
                    function.execute();

                    lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                    lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                    responseModel.setResCode(lpbResCode);
                    log.info(baseRequestDTO.getHeader().getMsgId() + " Excute Success: " + request.getBillingLog());
                } finally {
                    if (function != null) {
                        function.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception " + e.getMessage());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }
}
