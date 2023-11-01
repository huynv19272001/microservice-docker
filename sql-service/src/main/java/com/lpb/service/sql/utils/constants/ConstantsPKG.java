package com.lpb.service.sql.utils.constants;

import com.lpb.service.sql.model.ServiceDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ConstantsPKG {

	public static final String FUNCTION_LOG_BILLING = "{ ? = call PKG_ESB_BILLING.Fn_Complete_PayBill(?, ?, ?, ?) }";
	public static final String FUNCTION_LOG_TRANSFER = "{ ? = call PKG_ESB_TRANSFER.Fn_Complete_Transfer(?, ?, ?, ?) }";

	public static final String FUNCTION_VALIDATE_ESB = "{ ? = call PKG_ESB_UTIL.validateInput(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
	// PKG_VALIDATE_WITH DR
	public static final String FUNCTION_VALIDATE_ESB_DR = "{ ? = call PKG_ESB_UTIL.validateInput(?, ?, ?, ?, ?, ?, ?, ?, ?,?) }";

	public static final String FUNCTION_INIT_ESB = "{call PKG_ESB_UTIL.initRequest(?,?,?,?,?,?,?,?)}";
	public static final String FUNCTION_INIT_LOG_ESB = "{call PKG_ESB_UTIL.initRequest(?,?,?,?,?,?,?,?,?)}";
	public static final String FUNCTION_UPDATE_ESB = "{call PKG_ESB_UTIL.updateResponse(?, ?, ?, ?)}";

	public static final String FUNCTION_SWITCHING = "{? = call PKG_ESB_SWITCHING.Fn_Request_Transaction(?,?,?,?,?,?,?,?,?,?,?) }";

	public static final String FUNCTION_ADD_TRANS = "{? = call PKG_ESB_INIT_TRANSACTION.Fn_Init_Transaction(?,?,?,?,?,?,?,?,?,?,?) }";
	public static final String FUNCTION_OTP = "{? = call PKG_ESB_INIT_TRANSACTION.Fn_OTP(?,?,?,?,?,?) }";

	public static final String FUNCTION_LOAD_TXN = "{? = call PKG_ESB_SWITCHING.Fn_loadDetailTrans(?,?,?) }";

	public static final String PROC_ERROR_DESC = "{call PKG_ESB_UTIL.loadErrorDesc(?, ?)}";

	/**
	 * CONSTANT DR AND DC
	 */
	public static final String TYPE_DR = "DR";
	public static final String TYPE_DC = "DC";
	/**
	 * ESB CORE
	 */
	public static final String PROC_INSERT_LOG_UPLOAD_TRANSFER = "{call PKG_ESB_CORE_TRANSFER.prWriteLogTransfer(?,?,?,?)}";
	public static final String PROC_UPDATE_UPLOAD_TRANSFER = "{call PKG_ESB_CORE_TRANSFER.prUpdateLogTransfer(?,?,?,?)}";

	/**
	 * GENERATE SQL FOR TRANSACTION
	 *
	 * @param appMsgId
	 * @return
	 */

	public String genSqlLoadDetailTransfer(String appMsgId) {
		StringBuilder bu = new StringBuilder();
		bu.append("select t.*,");
		bu.append("   p.CONNECTOR_NAME, ");
		bu.append("    p.URL_API, ");
		bu.append("   p.CONNECTOR_URL, ");
		bu.append("    p.CONNECTOR_PORT, ");
		bu.append("   p.METHOD_ACTION, ");
		bu.append("   p.SERVICE_TYPE, ");
		bu.append("   p.EXECUTED_BY ");
		bu.append(" from (select s.SERVICE_ID, ");
		bu.append(" sp.PRODUCT_CODE, ");
		bu.append("    sp.CONNECTOR_NAME, ");
		bu.append("   sp.EXECUTED_BY, ");
		bu.append("  sp.URL_API, ");
		bu.append("  sp.CONNECTOR_URL, ");
		bu.append("   sp.CONNECTOR_PORT, ");
		bu.append("     sp.METHOD_ACTION, ");
		bu.append("      s.SERVICE_TYPE, ");
		bu.append(" sp.RECORD_STAT, ");
		bu.append("     p.HAS_ROLE ");
		bu.append(" from ESB_SERVICE s ");
		bu.append("     INNER JOIN ESB_PERMISSION p ");
		bu.append("   ON s.SERVICE_ID = p.SERVICE_ID ");
		bu.append(" INNER JOIN ESB_SERVICE_PROCESS sp ");
		bu.append("        ON sp.ROLE_ID = p.ROLE_ID) p, ");
		bu.append("  (select t.*, ");
		bu.append("  s.ROW_ID, ");
		bu.append("  s.SERVICE_ID, ");
		bu.append("  s.PRODUCT_CODE, ");
		bu.append("   s.TXN_ACCOUNT, ");
		bu.append("    s.TXN_AMOUNT, ");
		bu.append("  s.TXN_CCY, ");
		bu.append("   s.DRCR_IND, ");
		bu.append(" s.TRACE_NO ");

		bu.append(" from  (select T.APPMSG_ID, ");
		bu.append(" T.LAST_EVENT_SEQ_NO, ");
		bu.append("    T.LAST_PROC_ESR, ");
		bu.append("   T.TRN_BRANCH, ");
		bu.append("     T.EBK_USER, ");
		bu.append("    T.CUSTOMER_NO, ");
		bu.append("     T.REQUEST_BY, ");
		bu.append("   T.REQUEST_CHANEL, ");
		bu.append("   T.RESPONSE_BY, ");
		bu.append("    T.TRN_DESC, ");
		bu.append("    T.REQUEST_REF_NO ");

		bu.append(" from ESB_TRANSACTION t ");
		bu.append("  where t.TRN_STAT = 'I' ");
		bu.append("   and t.RECORD_STAT = 'O' ");
		bu.append("  and t.APPMSG_ID = '%s') t, ");
		bu.append("        (select s.rowid row_id, s.*,PKG_ESB_TRANSFER.init_Transfer(s.rowid,s.service_id) as TRACE_NO ");
		bu.append("          from ESB_TRANSACTION_TRANSFER s ");
		bu.append("     where s.RECORD_STAT = 'O' ");
		bu.append("        and s.TXN_STAT = 'I' ");
		bu.append("         and s.APPMSG_ID ='%s') s ");
		bu.append("         where t.APPMSG_ID = s.APPMSG_ID ) t ");
		bu.append("	 WHERE t.SERVICE_ID = p.SERVICE_ID ");
		bu.append("  and t.PRODUCT_CODE = p.PRODUCT_CODE ");
		bu.append("  and p.Has_Role = 'REQUEST_TXN' ");
		bu.append("	   and p.RECORD_STAT = 'O' ");

		String sql = String.format(bu.toString(), appMsgId, appMsgId);
        log.debug("QUERY: " + sql);

		return sql;
	}

	public static String genSqlLoadDetailBilling(String appMsgId) {
		StringBuilder bu = new StringBuilder();
		bu.append("select t.*, p.PRODUCT_CODE, p.CONNECTOR_NAME, p.URL_API, p.CONNECTOR_URL,p.CONNECTOR_PORT,");
		bu.append(" p.METHOD_ACTION,p.SERVICE_TYPE,p.EXECUTED_BY from (select s.SERVICE_ID, sp.PRODUCT_CODE, sp.CONNECTOR_NAME,sp.EXECUTED_BY,");
		bu.append(" sp.URL_API,sp.CONNECTOR_URL,sp.CONNECTOR_PORT,sp.METHOD_ACTION,s.SERVICE_TYPE,sp.RECORD_STAT,p.HAS_ROLE");
		bu.append(" from ESB_SERVICE s INNER JOIN ESB_PERMISSION p ON s.SERVICE_ID = p.SERVICE_ID INNER JOIN ESB_SERVICE_PROCESS sp");
		bu.append(" ON sp.ROLE_ID = p.ROLE_ID) p, (select t.* ");
		bu.append(" from TABLE(PKG_ESB_BILLING.Fn_Init_PayBill(CURSOR");
		bu.append(" (select t.*,");
		bu.append(" s.ROW_ID,");
		bu.append(" s.SERVICE_ID,");
		bu.append(" s.PRODUCT_CODE,");
		bu.append(" s.REQUEST_ACCOUNT,");
		bu.append(" s.RECEIVE_ACCOUNT,");
		bu.append(" s.BILL_INFO,");
		bu.append(" s.TOTAL_AMT");
		bu.append(" from (select T.APPMSG_ID,");
		bu.append(" T.LAST_EVENT_SEQ_NO,");
		bu.append(" T.LAST_PROC_ESR,");
		bu.append(" T.TRN_BRANCH,");
		bu.append(" T.EBK_USER,");
		bu.append(" T.CUSTOMER_NO,");
		bu.append(" T.REQUEST_BY,");
		bu.append(" T.REQUEST_CHANEL,");
		bu.append(" T.RESPONSE_BY,");
		bu.append(" T.TRN_DESC,");
		bu.append(" T.REQUEST_REF_NO,");
		bu.append(" '' AS TRACE_NO");
		bu.append(" from ESB_TRANSACTION t");
		bu.append(" where t.TRN_STAT = 'I'");
		bu.append(" and t.RECORD_STAT = 'O'");
		bu.append(" and t.APPMSG_ID =");
		bu.append(" '%s') t,");
		bu.append(" (select s.rowid row_id,");
		bu.append(" s.*");
		bu.append(" from ESB_TRANSACTION_BILLING s");
		bu.append(" where s.RECORD_STAT = 'O'");
		bu.append(" and s.PAY_STAT = 'I'");
		bu.append(" and s.APPMSG_ID =");
		bu.append(" '%s') s");
		bu.append(" where t.APPMSG_ID =");
		bu.append(" s.APPMSG_ID))) t) t");
		bu.append(" WHERE t.SERVICE_ID = p.SERVICE_ID");
		bu.append("  and t.PRODUCT_CODE = p.PRODUCT_CODE ");
		bu.append(" and p.Has_Role = 'REQUEST_TXN'");
		bu.append(" and p.RECORD_STAT = 'O'");
		String sql = String.format(bu.toString(), appMsgId, appMsgId);
        log.debug("SQL: " + sql);

		return sql;
	}

	public static String genSqlLoadDetailBillingRevert(String appMsgId) {
		StringBuilder bu = new StringBuilder();
		bu.append("select t.*, p.PRODUCT_CODE, p.CONNECTOR_NAME, p.URL_API, p.CONNECTOR_URL,p.CONNECTOR_PORT,");
		bu.append(" p.METHOD_ACTION,p.SERVICE_TYPE,p.EXECUTED_BY from (select s.SERVICE_ID, sp.PRODUCT_CODE, sp.CONNECTOR_NAME,sp.EXECUTED_BY,");
		bu.append(" sp.URL_API,sp.CONNECTOR_URL,sp.CONNECTOR_PORT,sp.METHOD_ACTION,s.SERVICE_TYPE,sp.RECORD_STAT,p.HAS_ROLE");
		bu.append(" from ESB_SERVICE s INNER JOIN ESB_PERMISSION p ON s.SERVICE_ID = p.SERVICE_ID INNER JOIN ESB_SERVICE_PROCESS sp");
		bu.append(" ON sp.ROLE_ID = p.ROLE_ID) p, (select t.* ");
		bu.append(" from TABLE(PKG_ESB_BILLING.Fn_Init_PayBill(CURSOR");
		bu.append(" (select t.*,");
		bu.append(" s.ROW_ID,");
		bu.append(" s.SERVICE_ID,");
		bu.append(" s.PRODUCT_CODE,");
		bu.append(" s.REQUEST_ACCOUNT,");
		bu.append(" s.RECEIVE_ACCOUNT,");
		bu.append(" s.BILL_INFO,");
		bu.append(" s.TOTAL_AMT");
		bu.append(" from (select T.APPMSG_ID,");
		bu.append(" T.LAST_EVENT_SEQ_NO,");
		bu.append(" T.LAST_PROC_ESR,");
		bu.append(" T.TRN_BRANCH,");
		bu.append(" T.EBK_USER,");
		bu.append(" T.CUSTOMER_NO,");
		bu.append(" T.REQUEST_BY,");
		bu.append(" T.REQUEST_CHANEL,");
		bu.append(" T.RESPONSE_BY,");
		bu.append(" T.TRN_DESC,");
		bu.append(" T.REQUEST_REF_NO,");
		bu.append(" '' AS TRACE_NO");
		bu.append(" from ESB_TRANSACTION t");
		bu.append(" where t.TRN_STAT = 'I'");
		bu.append(" and t.RECORD_STAT = 'O'");
		bu.append(" and t.APPMSG_ID =");
		bu.append(" '%s') t,");
		bu.append(" (select s.rowid row_id,");
		bu.append(" s.*");
		bu.append(" from ESB_TRANSACTION_BILLING s");
		bu.append(" where s.RECORD_STAT = 'O'");
		bu.append(" and s.PAY_STAT = 'I'");
		bu.append(" and s.APPMSG_ID =");
		bu.append(" '%s') s");
		bu.append(" where t.APPMSG_ID =");
		bu.append(" s.APPMSG_ID))) t) t");
		bu.append(" WHERE t.SERVICE_ID = p.SERVICE_ID");
		bu.append(" and p.Has_Role = 'REVERT_TXN'");
		bu.append(" and p.RECORD_STAT = 'O'");
		String sql = String.format(bu.toString(), appMsgId, appMsgId);
        log.debug("SQL: " + sql);

		return sql;
	}

	/**
	 * GENERATE SQL FOR QUERY
	 */

	public String genSQlQuery(String hasRole, String serviceID) {
		StringBuilder bu = new StringBuilder();
		bu.append("select sp.EXECUTED_BY,sp.CONNECTOR_NAME,sp.URL_API,sp.CONNECTOR_URL,sp.CONNECTOR_PORT,sp.METHOD_ACTION,");
		bu.append(" PKG_ESB_UTIL.loadSequencesPartner('%s') as TRACE_NO,sp.UDF1,sp.UDF2,sp.UDF3,sp.UDF4");
		bu.append(" from ESB_PERMISSION p INNER JOIN ESB_SERVICE s ON p.Service_Id = s.SERVICE_ID");
		bu.append(" INNER JOIN ESB_SERVICE_PROCESS sp ON p.ROLE_ID = sp.ROLE_ID");
		bu.append(" where p.HAS_ROLE='%s' and s.SERVICE_ID = '%s'");
		bu.append(" and (sp.UDF5='" + TYPE_DR + "' or sp.UDF5 = 'BOTH')");

		String sql = String
				.format(bu.toString(), serviceID, hasRole, serviceID);
		log.debug("sql: " + sql);

		return sql;
	}

	public String genSQlQuery(String hasRole, ServiceDTO serviceDto) {
		StringBuilder bu = new StringBuilder();
		bu.append("select sp.EXECUTED_BY,sp.CONNECTOR_NAME,sp.URL_API,sp.CONNECTOR_URL,sp.CONNECTOR_PORT,sp.METHOD_ACTION,");
		bu.append(" PKG_ESB_UTIL.loadSequencesPartner('%s') as TRACE_NO,sp.UDF1,sp.UDF2,sp.UDF3,sp.UDF4");
		bu.append(" from ESB_PERMISSION p INNER JOIN ESB_SERVICE s ON p.Service_Id = s.SERVICE_ID");
		bu.append(" INNER JOIN ESB_SERVICE_PROCESS sp ON p.ROLE_ID = sp.ROLE_ID");
		bu.append(" INNER JOIN ESB_SERVICE_PRODUCT p ON p.PRODUCT_CODE = sp.PRODUCT_CODE");
		bu.append(" where p.HAS_ROLE='%s' and s.SERVICE_ID = '%s' and p.PRODUCT_CODE = '%s'");
		bu.append(" and (sp.UDF5='" + TYPE_DR + "' or sp.UDF5 = 'BOTH')");

		String sql = String.format(bu.toString(), serviceDto.getSERVICE_ID(),
				hasRole, serviceDto.getSERVICE_ID(),
				serviceDto.getPRODUCT_CODE());
		log.debug("sql: " + sql);
		return sql;
	}

	public static String createSQLCommandInfo(String hasRole, ServiceDTO serviceDto) {
		StringBuilder bu = new StringBuilder();
		bu.append("select sp.EXECUTED_BY, ");
		bu.append("sp.CONNECTOR_NAME, ");
		bu.append("sp.URL_API, ");
		bu.append("sp.CONNECTOR_URL, ");
		bu.append("sp.CONNECTOR_PORT, ");
		bu.append("sp.METHOD_ACTION, ");
		bu.append("sp.UDF1, ");
		bu.append("sp.UDF2, ");
		bu.append("sp.UDF3, ");
		bu.append("sp.UDF4, ");
        bu.append("sp.UDF5, ");
        bu.append("sp.UDF6, ");
		bu.append("pa.idx, ");
		bu.append("pa.in_out, ");
		bu.append("pa.value_type, ");
		bu.append("pa.sql_clause, ");
		bu.append("pa.column_name, ");
		bu.append("pa.udf1 as PA_UDF1, ");
		bu.append("pa.udf2 as PA_UDF2, ");
		bu.append("pa.udf3 as PA_UDF3, ");
		bu.append("pa.udf4 as PA_UDF4, ");
		bu.append("e.sql_command ");
		bu.append("from ESB_PERMISSION p ");
		bu.append("INNER JOIN ESB_SERVICE s ");
		bu.append("ON p.Service_Id = s.SERVICE_ID ");
		bu.append("INNER JOIN ESB_SERVICE_PROCESS sp ");
		bu.append("ON p.ROLE_ID = sp.ROLE_ID ");
		bu.append("inner join esb_query_sql e ");
		bu.append("on e.cmd_id = sp.method_action ");
		bu.append("inner join esb_query_param pa ");
		bu.append("on e.cmd_id = pa.query_id ");
		bu.append("where p.HAS_ROLE = '%s' ");
		bu.append("and sp.SERVICE_ID = '%s' ");
		bu.append("and sp.product_code = '%s' ");
		bu.append(" and (sp.UDF5='" + TYPE_DR + "' or sp.UDF5 = 'BOTH')");

		String sql = String.format(bu.toString(), hasRole,
				serviceDto.getSERVICE_ID(), serviceDto.getPRODUCT_CODE());
		log.debug("sql: " + sql);
		return sql;
	}
}
