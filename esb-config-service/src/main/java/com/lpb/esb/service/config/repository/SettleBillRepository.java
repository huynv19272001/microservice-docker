package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Clob;
import java.util.List;

@Repository
public interface SettleBillRepository extends JpaRepository<EsbUserEntity, String> {

    @Query(value = "SELECT t.appmsg_id as transactionId, t.last_event_seq_no as lastEventSeqNo, t.last_proc_esr as lastProcEsr,\n" +
        "t.trn_branch as trnBranch, t.ebk_user as ebkUser, t.customer_no as customerNo, t.request_by as requestBy,\n" +
        "t.request_chanel as requestChanel, t.response_by as responseBy, t.trn_desc as trnDesc, t.request_ref_no as requestRefNo,\n" +
        "t.request_dt as requestDt, t.request_trn_code as requestTrnCode, t.req_confirm_stat as reqConfirmStat,\n" +
        "ROWIDTOCHAR(t.row_id) AS serviceRowId, t.service_id as serviceId, t.product_code as productCode,\n" +
        "t.txn_account as txnAccount, t.txn_amount as txnAmount,t.txn_ccy as txnCcy, t.drcr_ind as drcrInd,\n" +
        "t.trace_no as traceNo, p.connector_name as connectorName, p.url_api as urlApi, p.connector_url as connectorUrl,\n" +
        "p.connector_port as connectorPort, p.method_action as methodPort, p.service_type as serviceType,\n" +
        "p.executed_by as executedBy FROM (SELECT s.service_id, sp.product_code, sp.connector_name, sp.executed_by,\n" +
        "sp.url_api, sp.connector_url, sp.connector_port, sp.method_action, s.service_type, sp.record_stat,\n" +
        "p.has_role FROM esb_service s INNER JOIN esb_permission p ON s.service_id = p.service_id INNER JOIN \n" +
        "esb_service_process sp ON sp.role_id = p.role_id) p,(SELECT t.*, s.row_id, s.service_id, s.product_code,\n" +
        "s.txn_account, s.txn_amount, s.txn_ccy, s.drcr_ind, s.trace_no FROM ( SELECT t.appmsg_id, t.last_event_seq_no,\n" +
        "t.last_proc_esr, t.trn_branch, t.ebk_user, t.customer_no, t.request_by, t.request_chanel, t.response_by,\n" +
        "t.trn_desc, t.request_ref_no, t.request_dt, t.request_trn_code, t.req_confirm_stat  FROM esb_transaction t WHERE t.trn_stat = 'I' AND t.record_stat = 'O' \n" +
        "AND t.appmsg_id = :appMsgId) t, (SELECT s.rowid row_id,s.*, pkg_esb_transfer.init_transfer(s.rowid, s.service_id) \n" +
        "AS trace_no FROM esb_transaction_transfer s WHERE s.record_stat = 'O' AND s.txn_stat = 'I' AND s.appmsg_id = :appMsgId)\n" +
        "s WHERE t.appmsg_id = s.appmsg_id) t WHERE t.service_id = p.service_id AND t.product_code = p.product_code AND \n" +
        "p.has_role = 'REQUEST_TXN' AND p.record_stat = 'O'", nativeQuery = true)
    List<TransactionSettleBillTransfer> genSqlLoadDetailTransfer(@Param("appMsgId") String appMsgId);

    @Query(value = "SELECT t.appmsg_id AS transactionId, t.last_event_seq_no AS lastEventSeqNo, t.last_proc_esr AS lastProcEsr,\n" +
        "t.trn_branch AS trnBranch, t.ebk_user ebkUser, t.customer_no AS customerNo, t.request_by  AS requestBy,\n" +
        "t.request_chanel AS requestChanel, t.response_by AS serviceProvider, t.trn_desc AS trnDesc,\n" +
        "t.request_ref_no AS requestRefNo, t.trace_no AS traceNo, t.request_dt AS requestDt,\n" +
        "t.request_trn_code AS requestTrnCode, t.req_confirm_stat AS reqConfirmStat, ROWIDTOCHAR(t.row_id) AS serviceRowId,\n" +
        "t.service_id AS serviceId, t.product_code AS productCode, t.request_account AS requestAccount,\n" +
        "t.receive_account AS receiveAccount, t.bill_info AS billInfo, t.total_amt AS totalAmt,\n" +
        "p.connector_name AS connectorName, p.url_api AS urlApi, p.connector_url AS connectorUrl,\n" +
        "p.connector_port AS connectorPort, p.method_action AS methodAction, p.service_type AS serviceType,\n" +
        "p.executed_by AS executedBy FROM ( SELECT s.service_id, sp.product_code, sp.connector_name,\n" +
        "sp.executed_by, sp.url_api, sp.connector_url, sp.connector_port,sp.method_action,\n" +
        "s.service_type, sp.record_stat, p.has_role FROM esb_service s INNER JOIN esb_permission p ON s.service_id\n" +
        "= p.service_id INNER JOIN esb_service_process sp ON sp.role_id = p.role_id) p, (SELECT t.* FROM\n" +
        "(SELECT t.*, s.row_id, s.service_id, s.product_code, s.request_account, s.receive_account,\n" +
        "s.bill_info, s.total_amt FROM ( SELECT t.appmsg_id, t.last_event_seq_no, t.last_proc_esr,\n" +
        "t.trn_branch, t.ebk_user, t.customer_no, t.request_by, t.request_chanel, t.response_by,\n" +
        "t.trn_desc, t.request_ref_no, '' AS trace_no, t.request_dt, t.request_trn_code, t.req_confirm_stat\n" +
        "FROM esb_transaction t WHERE t.trn_stat = 'I' AND t.record_stat = 'O' AND t.appmsg_id = :appMsgId) t,\n" +
        "(SELECT s.rowid row_id, s.* FROM esb_transaction_billing s WHERE s.record_stat = 'O' AND s.pay_stat = 'I' \n" +
        "AND s.appmsg_id = :appMsgId) s WHERE t.appmsg_id = s.appmsg_id) t) t WHERE t.service_id = p.service_id\n" +
        "AND t.product_code = p.product_code AND p.has_role = 'REQUEST_TXN' AND p.record_stat = 'O'", nativeQuery = true)
    List<TransactionSettleBilling> genSqlLoadDetailBilling(@Param("appMsgId") String appMsgId);

    @Query(value = "select t.*, p.PRODUCT_CODE, p.CONNECTOR_NAME, p.URL_API, p.CONNECTOR_URL,p.CONNECTOR_PORT, p.METHOD_ACTION,p.SERVICE_TYPE,p.EXECUTED_BY from \n" +
        "(select s.SERVICE_ID, sp.PRODUCT_CODE, sp.CONNECTOR_NAME,sp.EXECUTED_BY, sp.URL_API,sp.CONNECTOR_URL,sp.CONNECTOR_PORT,sp.METHOD_ACTION,\n" +
        "s.SERVICE_TYPE,sp.RECORD_STAT,p.HAS_ROLE from ESB_SERVICE s INNER JOIN ESB_PERMISSION p ON s.SERVICE_ID = p.SERVICE_ID INNER JOIN \n" +
        "ESB_SERVICE_PROCESS sp ON sp.ROLE_ID = p.ROLE_ID) p, (select t.* from TABLE(PKG_ESB_BILLING.Fn_Init_PayBill(CURSOR (select t.*, s.ROW_ID, \n" +
        "s.SERVICE_ID, s.PRODUCT_CODE, s.REQUEST_ACCOUNT, s.RECEIVE_ACCOUNT, s.BILL_INFO, s.TOTAL_AMT from (select T.APPMSG_ID, T.LAST_EVENT_SEQ_NO, \n" +
        "T.LAST_PROC_ESR, T.TRN_BRANCH, T.EBK_USER, T.CUSTOMER_NO, T.REQUEST_BY, T.REQUEST_CHANEL, T.RESPONSE_BY, T.TRN_DESC, T.REQUEST_REF_NO, '' \n" +
        "AS TRACE_NO from ESB_TRANSACTION t where t.TRN_STAT = 'I' and t.RECORD_STAT = 'O' and t.APPMSG_ID = :appMsgId) t, (select s.rowid row_id, s.* from \n" +
        "ESB_TRANSACTION_BILLING s where s.RECORD_STAT = 'O' and s.PAY_STAT = 'I' and s.APPMSG_ID = :appMsgId) s where t.APPMSG_ID = s.APPMSG_ID))) t) t \n" +
        "WHERE t.SERVICE_ID = p.SERVICE_ID and p.Has_Role = 'REVERT_TXN' and p.RECORD_STAT = 'O'", nativeQuery = true)
    List<TransactionSettleBilling> genSqlLoadDetailBillingRevert(@Param("appMsgId") String appMsgId);

    @Query(value = "SELECT s.SERVICE_TYPE as serviceType from ESB_TRANSACTION t INNER JOIN ESB_SERVICE s ON t.SERVICE_ID = s.SERVICE_ID" +
        " where t.APPMSG_ID = :appMsgId", nativeQuery = true)
    ServiceType loadServiceType(@Param("appMsgId") String appMsgId);

    @Query(value = "SELECT t.SERVICE_TYPE as serviceType FROM ESB_SERVICE t where t.SERVICE_ID = :serviceId", nativeQuery = true)
    ServiceType loadServiceTypeByService(@Param("serviceId") String serviceId);

    interface TransactionSettleBillTransfer {
        String getTransactionId();

        String getLastEventSeqNo();

        String getLastProcEsr();

        String getTrnBranch();

        String getEbkUser();

        String getCustomerNo();

        String getRequestBy();

        String getRequestChanel();

        String getResponseBy();

        String getTrnDesc();

        String getRequestRefNo();

        String getRequestTrnCode();

        String getRequestDt();

        String getReqConfirmStat();

        String getServiceRowId();

        String getServiceId();

        String getProductCode();

        Clob getTxnAccount();

        String getTxnAmount();

        String getTxnCcy();

        String getDrcrInd();

        String getTraceNo();

        String getConnectorName();

        String getUrlApi();

        String getConnectorUrl();

        String getConnectorPort();

        String getMethodPort();

        String getServiceType();

        String getExecutedBy();

    }

    interface TransactionSettleBilling {
        String getTransactionId();

        String getLastEventSeqNo();

        String getLastProcEsr();

        String getTrnBranch();

        String getEbkUser();

        String getCustomerNo();

        String getRequestBy();

        String getRequestChanel();

        String getServiceProvider();

        String getTrnDesc();

        String getRequestRefNo();

        String getTraceNo();

        String getRequestDt();

        String getRequestTrnCode();

        String getReqConfirmStat();

        String getServiceRowId();

        String getServiceId();

        String getProductCode();

        String getRequestAccount();

        String getReceiveAccount();

        Clob getBillInfo();

        String getTotalAmt();

        String getConnectorName();

        String getUrlApi();

        String getConnectorUrl();

        String getConnectorPort();

        String getMethodAction();

        String getServiceType();

        String getExecutedBy();
    }

    interface ServiceType {
        String getServiceType();
    }

}
