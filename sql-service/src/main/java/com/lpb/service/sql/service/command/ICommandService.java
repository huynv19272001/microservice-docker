package com.lpb.service.sql.service.command;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.model.FcubsErrorDTO;

import java.sql.Connection;
import java.util.List;

public interface ICommandService {
	/**
	 * ONLY INSERT OR UPDATE,DELETE
	 *
	 * @param conn
	 * @param cmdSQL
	 * @param listField
	 * @return
	 */
	boolean dmlCommand(Connection conn, String cmdSQL, List<ParamBean> listField, FcubsErrorDTO fcubsError);

	/**
	 * CALL PACKAGE RETURN OUPUT AND INOUT support xmltype,cursor,String,int
	 *
	 * @param conn
	 * @param funcName
	 * @param listField
	 * @param fcubsError
	 * @return
	 */
    ResponseModel execPackage(Connection conn, String funcName, List<ParamBean> listField);

	/**
	 * CALL PACKAGE RETURN OUPUT AND INOUT support xmltype,cursor,String,int
	 *
	 * @param conn
	 * @param funcName
	 * @param listField
	 * @param fcubsError
	 * @return
	 */
    ResponseModel execPackageMySQL(Connection conn, String funcName, List<ParamBean> listField);

	/**
	 * SELECT DATA
	 *
	 * @param conn
	 * @param cmdSQL
	 * @param listField
	 * @return
	 */
    ResponseModel selectCMD(Connection conn, String cmdSQL, List<ParamBean> listField);

	/**
	 * EXECUTE BATCH
	 *
	 */
	void processBatch(List<String> listSql, FcubsErrorDTO fcubsError);

}
