package com.lpb.service.sql.service.command.dao;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.utils.constants.ConstantsPKG;
import com.lpb.service.sql.utils.constants.EnumService;
import com.lpb.service.sql.model.*;
import com.lpb.service.sql.utils.Utilities;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ESBQueryDAO extends ConstantsPKG {

    public QueryVO loadConnectorQuery(String hasRole, String serviceID, FcubsErrorDTO fcubsError) {
        QueryVO queryObj = new QueryVO();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // conn = ConnectionDB.openESB();
            String sql = new ESBQueryDAO().genSQlQuery(hasRole, serviceID);
            stm = conn.prepareStatement(sql);
            stm.setQueryTimeout(10);
            rs = stm.executeQuery();
            while (rs.next()) {
                queryObj.setCONNECTOR_NAME(rs.getString("CONNECTOR_NAME"));
                queryObj.setCONNECTOR_PORT(rs.getString("CONNECTOR_PORT"));
                queryObj.setCONNECTOR_URL(rs.getString("CONNECTOR_URL"));
                queryObj.setMETHOD_ACTION(rs.getString("METHOD_ACTION"));
                queryObj.setSERVICE_ID(serviceID);
                queryObj.setURL_API(rs.getString("URL_API"));
                queryObj.setEXECUTED_BY(rs.getString("EXECUTED_BY"));
                queryObj.setTRACE_NO(rs.getString("TRACE_NO"));
                queryObj.setUDF1(rs.getString("UDF1") != null ? rs.getString("UDF1") : null);
                queryObj.setUDF2(rs.getString("UDF2") != null ? rs.getString("UDF2") : null);
                queryObj.setUDF3(rs.getString("UDF3") != null ? rs.getString("UDF3") : null);
                queryObj.setUDF4(rs.getString("UDF4") != null ? rs.getString("UDF4") : null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Exception ESB Switching: " + ex.getMessage());
            fcubsError.setECODE("ESB-99");
            fcubsError.setEDESC(ex.getMessage());
        } finally {
            // ConnectionDB.closeAll(conn, stm, rs);
        }
        return queryObj;
    }

    public QueryVO loadConProduct(String hasRole, ServiceDTO serviceDto, FcubsErrorDTO fcubsError) {
        QueryVO queryObj = new QueryVO();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            // conn = ConnectionDB.openESB();
            String sql = new ESBQueryDAO().genSQlQuery(hasRole, serviceDto);
            stm = conn.prepareStatement(sql);
            stm.setQueryTimeout(10);
            rs = stm.executeQuery();
            while (rs.next()) {
                queryObj.setCONNECTOR_NAME(rs.getString("CONNECTOR_NAME"));
                queryObj.setCONNECTOR_PORT(rs.getString("CONNECTOR_PORT"));
                queryObj.setCONNECTOR_URL(rs.getString("CONNECTOR_URL"));
                queryObj.setMETHOD_ACTION(rs.getString("METHOD_ACTION"));
                queryObj.setSERVICE_ID(serviceDto.getSERVICE_ID());
                queryObj.setURL_API(rs.getString("URL_API"));
                queryObj.setEXECUTED_BY(rs.getString("EXECUTED_BY"));
                queryObj.setTRACE_NO(rs.getString("TRACE_NO"));
                queryObj.setUDF1(rs.getString("UDF1") != null ? rs.getString("UDF1") : null);
                queryObj.setUDF2(rs.getString("UDF2") != null ? rs.getString("UDF2") : null);
                queryObj.setUDF3(rs.getString("UDF3") != null ? rs.getString("UDF3") : null);
                queryObj.setUDF4(rs.getString("UDF4") != null ? rs.getString("UDF4") : null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Exception ESB Switching: " + ex.getMessage());
            fcubsError.setECODE("ESB-99");
            fcubsError.setEDESC(ex.getMessage());
        } finally {
            // ConnectionDB.closeAll(conn, stm, rs);
        }
        return queryObj;
    }

    public EnumService loadTypeTransaction(String serviceID, FcubsErrorDTO fcubsError) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT SERVICE_TYPE FROM ESB_SERVICE WHERE SERVICE_ID = ?";
        EnumService typeService = null;
        try {
            // conn = ConnectionDB.openESB();
            stm = conn.prepareStatement(sql);
            stm.setString(1, serviceID);
            stm.setQueryTimeout(30);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (rs.getString("SERVICE_TYPE").equals("TRANSFER")) {
                    typeService = EnumService.TRANSFER;
                } else {
                    typeService = EnumService.BILLING;
                }
            }
        } catch (Exception ex) {
            fcubsError.setECODE("ESB-99");
            fcubsError.setEDESC(ex.getMessage());
            log.debug("Exception loadType: " + ex.getMessage());
            typeService = EnumService.BILLING;
        } finally {
            // ConnectionDB.closeAll(conn, stm, rs);
        }

        return typeService;

    }


    public ResponseModel loadSQLInfo(Connection conn, String hasRole, ServiceDTO serviceDto) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        QueryVO queryObj = new QueryVO();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            // conn = ConnectionDB.openESB();
            String sql = new ESBQueryDAO().createSQLCommandInfo(hasRole, serviceDto);
            stm = conn.prepareStatement(sql);
            stm.setQueryTimeout(30);
            rs = stm.executeQuery();
            List<SQLParamDTO> listParam = new ArrayList<SQLParamDTO>();
            SQLInfoDTO sqlInfoDto = new SQLInfoDTO();
            while (rs.next()) {
                SQLParamDTO paramDto = new SQLParamDTO();

                queryObj.setCONNECTOR_NAME(rs.getString("CONNECTOR_NAME"));
                queryObj.setCONNECTOR_PORT(rs.getString("CONNECTOR_PORT"));
                queryObj.setCONNECTOR_URL(rs.getString("CONNECTOR_URL"));
                queryObj.setMETHOD_ACTION(rs.getString("METHOD_ACTION"));
                queryObj.setSERVICE_ID(serviceDto.getSERVICE_ID());
                queryObj.setURL_API(rs.getString("URL_API"));
                queryObj.setEXECUTED_BY(rs.getString("EXECUTED_BY"));
                queryObj.setUDF1(rs.getString("UDF1") != null ? rs.getString("UDF1") : null);
                queryObj.setUDF2(rs.getString("UDF2") != null ? rs.getString("UDF2") : null);
                queryObj.setUDF3(rs.getString("UDF3") != null ? rs.getString("UDF3") : null);
                queryObj.setUDF4(rs.getString("UDF4") != null ? rs.getString("UDF4") : null);
                queryObj.setUDF5(rs.getString("UDF5") != null ? rs.getString("UDF5") : null);
                queryObj.setUDF6(rs.getString("UDF6") != null ? rs.getString("UDF6") : null);

                // load Param
                Clob clobCommand = rs.getClob("SQL_COMMAND");
                sqlInfoDto.setSqlCommand(Utilities.convertClobToStr(clobCommand));

                paramDto.setColName(rs.getString("COLUMN_NAME"));
                paramDto.setInOut(rs.getString("IN_OUT"));
                paramDto.setIdx(rs.getInt("IDX"));
                paramDto.setValType(rs.getString("VALUE_TYPE"));
                paramDto.setSqlClause(rs.getString("SQL_CLAUSE"));
                paramDto.setUdf1(rs.getString("PA_UDF1"));
                paramDto.setUdf2(rs.getString("PA_UDF2"));
                paramDto.setUdf3(rs.getString("PA_UDF3"));
                paramDto.setUdf4(rs.getString("PA_UDF4"));

                listParam.add(paramDto);
            }

            if (listParam.size() < 1) {
                resCode = LpbResCode.builder().errorCode("27").build();
            } else {
                sqlInfoDto.setListParamSQL(listParam);
                queryObj.setSqlInfo(sqlInfoDto);
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).build();
            }
            resCode = loadErrorDesc(conn, resCode);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception loadSQLInfo: " + ex.getMessage());

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception loadSQLInfo: " + ex.getMessage()).build();
        } finally {
            // ConnectionDB.closeAll(conn, stm, rs);
        }
        responseModel = ResponseModel.builder().resCode(resCode).data(queryObj).build();
        return responseModel;
    }

    public LpbResCode loadErrorDesc(Connection conn, LpbResCode resCode) {
        CallableStatement cstm = null;
        try {
            cstm = conn.prepareCall(ConstantsPKG.PROC_ERROR_DESC);
            cstm.setQueryTimeout(10);
            cstm.setString(1, resCode.getErrorCode().replace("00", "0"));
            log.info("ECODE: " + resCode.getErrorCode());
            cstm.registerOutParameter(1, Types.VARCHAR);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.execute();

            // resCode.setErrorCode(cstm.getString(1));
            resCode.setErrorDesc(cstm.getString(2));
            log.info("ERROR CODE: " + resCode.getErrorCode());
            log.info("ERROR CODE: " + resCode.getErrorDesc());
        } catch (Exception ex) {
            log.error("EXCEPTION LOAD ERROR: " + ex.getMessage());
            resCode.setErrorCode(EsbErrorCode.TIME_OUT.label);
            resCode.setErrorDesc("TIMEOUT");
        } finally {
            // ConnectionDB.closeAll(conn, cstm);
        }
        return resCode;
    }
}
