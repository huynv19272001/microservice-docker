package com.esb.transaction.respository.impl;

import com.esb.transaction.constants.Constant;
import com.esb.transaction.dto.*;
import com.esb.transaction.respository.ITransactionDAO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
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
import java.util.Map;

@Log4j2
@Repository
public class ITransactionDAOImpl implements ITransactionDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseModel updateTransaction(UpdateTransactionDTO transactionDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        SimpleJdbcCall jdbcCall;
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constant.PKG_ESB_INIT_TRANSACTION)
                .withProcedureName(Constant.PR_UPDATE_TRANSACTION);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_AppMsgID", transactionDTO.getAppMsgId())
                .addValue("p_valueDt", transactionDTO.getValueDt())
                .addValue("p_coreRefNo", transactionDTO.getCoreRefNo())
                .addValue("p_msgID", transactionDTO.getMessageId())
                .addValue("p_errorCode", transactionDTO.getErrorCode())
                .addValue("p_errorDesc", transactionDTO.getErrorDesc());

            Map results = jdbcCall.execute(paramMap);
            lpbResCode.setErrorCode((String) results.get("P_ERRORCODE"));
            if (lpbResCode.getErrorCode().equals("ESB-000")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc((String) results.get("P_ERRORDESC"));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc((String) results.get("P_ERRORDESC"));
            }
            log.info(transactionDTO.getAppMsgId() + " Update Transaction " + lpbResCode.getErrorDesc());
            responseModel.setResCode(lpbResCode);
            return responseModel;
        } catch (Exception e) {
            log.info(transactionDTO.getAppMsgId() + " Exception Update Transaction: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel initTransaction(String userID, String trnBranch, String trnDesc,
                                         String appMsgId, String xmltypeCustomerInfo,
                                         String xmlTypePartnerInfo, String xmlTypeService,
                                         String xmlTypeTrnPost) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        try {
            Session session = this.entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                try {
                    CallableStatement function = connection.prepareCall(
                        "{? = call PKG_ESB_INIT_TRANSACTION.Fn_Init_Transaction(?,?,?,?,?,XMLType(?),XMLType(?),XMLType(?),XMLType(?),?,?)}");
                    function.registerOutParameter(1, Types.VARCHAR);
                    function.setString(2, userID);
                    function.setString(3, "Y");
                    function.setString(4, trnBranch);
                    function.setString(5, trnDesc);
                    function.setString(6, appMsgId);
                    function.setString(7, xmltypeCustomerInfo);
                    function.setString(8, xmlTypePartnerInfo);
                    function.setString(9, xmlTypeService);
                    function.setString(10, xmlTypeTrnPost);
                    function.registerOutParameter(11, Types.VARCHAR);
                    function.registerOutParameter(12, Types.VARCHAR);

                    function.execute();

                    FCubsErrorDTO fCubsError = new FCubsErrorDTO();
                    fCubsError.setECode(function.getString(11));
                    fCubsError.setEDesc(function.getString(12));

                    if (function.getString(1).equals("Y")) {
                        lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                        lpbResCode.setErrorDesc(fCubsError.getEDesc());
                    } else {
                        lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                        lpbResCode.setErrorDesc(fCubsError.getEDesc());
                    }
                    log.info(appMsgId + " InitTransaction " + lpbResCode.getErrorDesc());
                    responseModel.setResCode(lpbResCode);
                    if (function != null) {
                        function.close();
                    }
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            });
            return responseModel;
        } catch (Exception e) {
            log.info(appMsgId + " Exception Init Transaction: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }
}
