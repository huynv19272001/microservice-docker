package com.lpb.esb.service.transaction.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.transaction.constant.ServiceApiConfig;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Types;

@Component
@Log4j2
public class TransactionUtils {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager entityManager;

    public ResponseModel<ExecuteModel<BigDecimal>> getNextSequenceSystemLog() throws JsonProcessingException {
        ResponseEntity<String> res = restTemplate.getForEntity(serviceApiConfig.getApiSequenceNext(), String.class);
        String body = res.getBody();
        ResponseModel<ExecuteModel<BigDecimal>> responseModel = objectMapper.readValue(body, new TypeReference<ResponseModel<ExecuteModel<BigDecimal>>>() {
        });
        log.info("Response [{}] for next seq: {}", res.getStatusCodeValue(), responseModel);
        return responseModel;
    }

    public ExecuteModel requestTransaction(String userID, String trnBranch, String trnDesc, String appMsgId, String confirmReq,
                                           String txnCode, String xmltypeCustomerInfo, String xmlTypePartnerInfo, String xmlTypeService) {
        ExecuteModel executeModel = new ExecuteModel();

        try {
            Session session = this.entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                try {
                    CallableStatement function = connection.prepareCall(
                        "{? = call PKG_ESB_SWITCHING.Fn_Request_Transaction(?,?,?,?,XMLType(?),?,XMLType(?),XMLType(?),?,?,?)}");
                    function.registerOutParameter(1, Types.VARCHAR);
                    function.setString(2, userID);
                    function.setString(3, trnBranch);
                    function.setString(4, trnDesc);
                    function.setString(5, appMsgId);
                    function.setString(6, xmltypeCustomerInfo);
                    function.setString(7, confirmReq);
                    function.setString(8, xmlTypePartnerInfo);
                    function.setString(9, xmlTypeService);
                    function.setString(10, txnCode);
                    function.registerOutParameter(11, Types.VARCHAR);
                    function.registerOutParameter(12, Types.VARCHAR);

                    function.execute();

                    if (function.getString(1).equals("Y"))
                        executeModel.setExecuteCode(ExecuteCode.SUCCESS);
                    else {
                        executeModel.setExecuteCode(ExecuteCode.FAIL);
                        executeModel.setMessage(function.getString(11) + " " + function.getString(12));
                    }
                    if (function != null) {
                        function.close();
                    }
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            });
        } catch (Exception ex) {
            executeModel.setExecuteCode(ExecuteCode.FAIL);
            executeModel.setMessage(ex.getMessage());
        }
        return executeModel;
    }
}
