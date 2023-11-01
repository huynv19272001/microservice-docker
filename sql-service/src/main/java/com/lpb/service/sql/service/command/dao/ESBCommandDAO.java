package com.lpb.service.sql.service.command.dao;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.model.bean.ResultBean;
import com.lpb.service.sql.utils.constants.Constants;
import com.lpb.service.sql.utils.constants.EnumInOutField;
import com.lpb.service.sql.model.FcubsErrorDTO;
import com.lpb.service.sql.utils.Utilities;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleConnectionWrapper;
import oracle.jdbc.OracleTypes;
import oracle.xdb.XMLType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ESBCommandDAO {

    public boolean insertOrUpdate(Connection conn, String cmdSQL, List<ParamBean> listField, FcubsErrorDTO fcubsError) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean inOrUp = false;
        try {
            stm = conn.prepareStatement(cmdSQL);
            for (ParamBean paramBean : listField) {
                switch (paramBean.getTypeField()) {
                    case VARCHAR:
                        stm.setString(paramBean.getIdx(), paramBean.getValField());
                        break;
                    case INT:
                        stm.setInt(paramBean.getIdx(), Integer.parseInt(paramBean.getValField()));
                        break;
                    case DOUBLE:
                        stm.setDouble(paramBean.getIdx(), Double.parseDouble(paramBean.getValField()));
                        break;
                    default:
                        stm.setString(paramBean.getIdx(), paramBean.getValField());
                        break;
                }
            }
            stm.setQueryTimeout(60);
            inOrUp = stm.execute();
            fcubsError.setECODE("ESB-000");
            fcubsError.setEDESC("SUCCESS");
        } catch (SQLTimeoutException sqlEx) {
            fcubsError.setECODE("ESB-090");
            fcubsError.setEDESC("TIME OUT");
            log.error("Exception execPackage: " + sqlEx.getMessage());
        } catch (Exception ex) {
            fcubsError.setECODE("ESB-099");
            fcubsError.setEDESC(ex.getMessage());
            log.error("Exception insertOrUpdate: " + ex.getMessage());
        } finally {
            //ConnectionDB.closeAll(conn, stm, rs);
        }
        return inOrUp;
    }

    public ResponseModel execPackage(Connection conn, String funcName, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        CallableStatement stm = null;
        OracleConnection oracleConnection = null;
        OracleCallableStatement stmt = null;
        List<ResultBean> listRs = new ArrayList<ResultBean>();
        Clob reqClob = null;
        try {
            if (conn.isWrapperFor(OracleConnection.class)) {
                oracleConnection = conn.unwrap(OracleConnection.class);
            }

            stm = oracleConnection.prepareCall(funcName);
            for (ParamBean paramBean : listField) {
                if (paramBean.getInOutField() == EnumInOutField.INPUT) {
                    switch (paramBean.getTypeField()) {
                        case VARCHAR:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setString(paramBean.getIdx(), paramBean.getValField());
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.VARCHAR);
                            }
                            break;
                        case INT:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setInt(paramBean.getIdx(), Integer.valueOf(paramBean.getValField()));
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.INTEGER);
                            }

                            break;
                        case DOUBLE:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setDouble(paramBean.getIdx(), Double.valueOf(paramBean.getValField()));
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.DOUBLE);
                            }

                            break;
                        case DATE:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrToSQLDate(paramBean.getValField()));
                            break;
                        case DATETIME:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrtoSQLDateTime(paramBean.getValField()));
                            break;

                        case TIMESTAMP:
                            stm.setTimestamp(paramBean.getIdx(), Utilities.convertStrToSqlTimeStamp(paramBean.getValField()));
                            break;

                        case XMLTYPE:
                            XMLType xmlVal = XMLType.createXML(oracleConnection, paramBean.getValField());
                            stm.setObject(paramBean.getIdx(), xmlVal);
                            break;
                        case CLOB:
                            reqClob = conn.createClob();
                            reqClob.setString(1, paramBean.getValField());
                            stm.setClob(paramBean.getIdx(), reqClob);
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            break;
                    }
                } else if (paramBean.getInOutField() == EnumInOutField.OUTPUT) {
                    switch (paramBean.getTypeField()) {
                        case CLOB:
                            stm.registerOutParameter(paramBean.getIdx(), Types.CLOB);
                            break;
                        case VARCHAR:
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                        case INT:
                            stm.registerOutParameter(paramBean.getIdx(), Types.INTEGER);
                            break;
                        case CURSOR:
                            stm.registerOutParameter(paramBean.getIdx(), OracleTypes.CURSOR);
                            break;
                        case XMLTYPE:
                            stm.registerOutParameter(paramBean.getIdx(), OracleTypes.OPAQUE, "SYS.XMLTYPE");
                            break;
                        default:
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                    }
                } else if (paramBean.getInOutField() == EnumInOutField.INOUT) {
                    switch (paramBean.getTypeField()) {
                        case CLOB:
                            reqClob = conn.createClob();
                            reqClob.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.setClob(paramBean.getIdx(), reqClob);
                            stm.registerOutParameter(paramBean.getIdx(), Types.CLOB);
                            break;
                        case VARCHAR:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                        case INT:
                            stm.setInt(paramBean.getIdx(), Integer.parseInt(paramBean.getValField()));
                            stm.registerOutParameter(paramBean.getIdx(), Types.INTEGER);
                            break;
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                    }
                } else {
                    switch (paramBean.getTypeField()) {
                        case CLOB:

                            stm.registerOutParameter(paramBean.getIdx(), Types.CLOB);
                            break;
                        case VARCHAR:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                        case INT:
                            stm.setInt(paramBean.getIdx(), Integer.parseInt(paramBean.getValField()));
                            stm.registerOutParameter(paramBean.getIdx(), Types.INTEGER);
                            break;
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                    }
                }
            }
            stm.setQueryTimeout(60);
            log.error("REQUEST PACKAGE : " + funcName);
            stm.execute();
            log.error("RESPONSE PACKAGE : " + funcName);
            // START READ RESPONSE PACKAGE
            for (ParamBean paramBean : listField) {
                if ((paramBean.getInOutField() == EnumInOutField.OUTPUT) || (paramBean.getInOutField() == EnumInOutField.INOUT)) {

                    ResultBean rs = new ResultBean();
                    switch (paramBean.getTypeField()) {
                        case VARCHAR:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getString(paramBean.getIdx()));
                            break;
                        case INT:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getInt(paramBean.getIdx()));
                            break;
                        // CAST TO XMLTYPE
                        case XMLTYPE:
                            rs.setIdx(paramBean.getIdx());
                            stmt = (OracleCallableStatement) stm;
                            XMLType xmlVal = XMLType.createXML(stmt.getOPAQUE(paramBean.getIdx()));
                            rs.setValObj(xmlVal.getStringVal());
                            break;
                        // CAST to RESULTSET
                        case CURSOR:
                            rs.setIdx(paramBean.getIdx());
                            ResultSet resultSet = null;
                            resultSet = (ResultSet) stm.getObject(paramBean.getIdx());
                            rs.setValObj(convertCursorToXML(resultSet));
                            if (resultSet != null) {
                                try {
                                    resultSet.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                            break;
                        default:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getString(paramBean.getIdx()));
                            break;
                    }
                    listRs.add(rs);
                }
            }
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        } catch (SQLTimeoutException sqlEx) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.TIME_OUT.label).errorDesc("TIME OUT").build();
            log.error("Exception execPackage: " + sqlEx.getMessage());
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception execPackage: " + ex.getMessage()).build();
            log.error("Exception execPackage: " + ex.getMessage());
        } finally {
            try {
                if (reqClob != null) reqClob.free();
            } catch (SQLException e) {
                log.error("Exception SQLException: " + e.getMessage());

            } catch (Exception ex) {
                log.info("Exception : " + ex.getMessage());
            }
        }
        responseModel = ResponseModel.builder().resCode(resCode).data(listRs).build();
        return responseModel;
    }

    public ResponseModel execPackageMySQL(Connection conn, String funcName, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        CallableStatement stm = null;
        List<ResultBean> listRs = new ArrayList<ResultBean>();
        Clob reqClob = null;

        try {
            stm = conn.prepareCall(funcName);
            for (ParamBean paramBean : listField) {
                if (paramBean.getInOutField() == EnumInOutField.INPUT) {
                    switch (paramBean.getTypeField()) {
                        case VARCHAR:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setString(paramBean.getIdx(), paramBean.getValField());
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.VARCHAR);
                            }
                            break;
                        case INT:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setInt(paramBean.getIdx(), Integer.valueOf(paramBean.getValField()));
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.INTEGER);
                            }

                            break;
                        case DOUBLE:
                            if (paramBean.getValField().trim().length() >= 1) {
                                stm.setDouble(paramBean.getIdx(), Double.valueOf(paramBean.getValField()));
                            } else {
                                stm.setNull(paramBean.getIdx(), Types.DOUBLE);
                            }

                            break;
                        case DATE:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrToSQLDate(paramBean.getValField()));
                            break;
                        case DATETIME:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrtoSQLDateTime(paramBean.getValField()));
                            break;

                        case TIMESTAMP:
                            stm.setTimestamp(paramBean.getIdx(), Utilities.convertStrToSqlTimeStamp(paramBean.getValField()));
                            break;
                        case XMLTYPE:
                            XMLType xmlVal = XMLType.createXML(conn, paramBean.getValField());
                            stm.setObject(paramBean.getIdx(), xmlVal);
                            break;
                        case CLOB:
                            reqClob = conn.createClob();
                            reqClob.setString(1, paramBean.getValField());
                            stm.setClob(paramBean.getIdx(), reqClob);
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            break;
                    }
                } else if (paramBean.getInOutField() == EnumInOutField.OUTPUT) {
                    switch (paramBean.getTypeField()) {
                        case CLOB:
                            stm.registerOutParameter(paramBean.getIdx(), Types.CLOB);
                            break;
                        case VARCHAR:
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                        case INT:
                            stm.registerOutParameter(paramBean.getIdx(), Types.INTEGER);
                            break;
                        case CURSOR:
                            stm.registerOutParameter(paramBean.getIdx(), OracleTypes.CURSOR);
                            break;
                        case XMLTYPE:
                            stm.registerOutParameter(paramBean.getIdx(), OracleTypes.OPAQUE, "SYS.XMLTYPE");
                            break;
                        case RESULT_SET:
                            break;
                        default:
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                    }
                } else {
                    switch (paramBean.getTypeField()) {
                        case CLOB:
                            stm.registerOutParameter(paramBean.getIdx(), Types.CLOB);
                            break;
                        case VARCHAR:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                        case INT:
                            stm.setInt(paramBean.getIdx(), Integer.parseInt(paramBean.getValField()));
                            stm.registerOutParameter(paramBean.getIdx(), Types.INTEGER);
                            break;
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            stm.registerOutParameter(paramBean.getIdx(), Types.VARCHAR);
                            break;
                    }
                }
            }
            stm.setQueryTimeout(60);
            log.error("REQUEST PACKAGE : " + funcName);

            ResultSet resultSet = null;
            resultSet = stm.executeQuery();

            log.error("RESPONSE PACKAGE : " + funcName);

            // START READ RESPONSE PACKAGE
            for (ParamBean paramBean : listField) {
                if ((paramBean.getInOutField() == EnumInOutField.OUTPUT) || (paramBean.getInOutField() == EnumInOutField.INOUT)) {

                    ResultBean rs = new ResultBean();
                    switch (paramBean.getTypeField()) {
                        case VARCHAR:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getString(paramBean.getIdx()));
                            break;
                        case INT:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getInt(paramBean.getIdx()));
                            break;
                        case CURSOR:
                            rs.setIdx(paramBean.getIdx());

                            ResultSet result = null;
                            result = (ResultSet) stm.getObject(paramBean.getIdx());

                            rs.setValObj(convertCursorToXML(result));
                            if (result != null) {
                                try {
                                    result.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                            break;
                        case RESULT_SET:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(convertCursorToXML(resultSet));

                            if (resultSet != null) {
                                try {
                                    resultSet.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            break;
                        default:
                            rs.setIdx(paramBean.getIdx());
                            rs.setValObj(stm.getString(paramBean.getIdx()));
                            break;
                    }
                    listRs.add(rs);
                }
            }

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        } catch (SQLTimeoutException sqlEx) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.TIME_OUT.label).errorDesc("TIME OUT").build();
            log.error("Exception execPackage: " + sqlEx.getMessage());
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception execPackage: " + ex.getMessage()).build();
            log.error("Exception execPackage: " + ex.getMessage());
        } finally {
            try {
                if (reqClob != null) reqClob.free();
            } catch (SQLException e) {
                log.error("Exception SQLException: " + e.getMessage());
            } catch (Exception ex) {
                log.error("Exception : " + ex.getMessage());
            }
        }
        responseModel = ResponseModel.builder().resCode(resCode).data(listRs).build();
        return responseModel;
    }

    @SuppressWarnings("unused")
    private void convertCursorToMap(ResultSet rs, Map<String, List<Object>> mapCursor) {
        List<Object> list4Map;
        try {
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    if (mapCursor.containsKey(rsmd.getColumnLabel(i))) {
                        list4Map = mapCursor.get(rsmd.getColumnLabel(i));
                    } else {
                        list4Map = new ArrayList<Object>();

                    }
                    list4Map.add(rs.getObject(i));
                    mapCursor.put(rsmd.getColumnLabel(i), list4Map);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String convertCursorToXML(ResultSet rs) {
        StringBuilder strBuilder = new StringBuilder();
        try {
            strBuilder.append("<ROWS>");
            while (rs.next()) {
                strBuilder.append("<ROW>");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    try {
                        log.info(rs.getObject(i));
                        XMLType objectXML = (XMLType) rs.getObject(i);
                        if (objectXML != null && objectXML.getString() != "") {
                            strBuilder.append("<" + rsmd.getColumnLabel(i) + ">" + objectXML.getString() + "</" + rsmd.getColumnLabel(i) + ">");
                        } else {
                            strBuilder.append("<" + rsmd.getColumnLabel(i) + ">" + this.ReplaceSpecific(rs.getObject(i)) + "</" + rsmd.getColumnLabel(i) + ">");
                        }
                    } catch (Exception ex) {
                        strBuilder.append("<" + rsmd.getColumnLabel(i) + ">" + this.ReplaceSpecific(rs.getObject(i)) + "</" + rsmd.getColumnLabel(i) + ">");
                    }

                }
                strBuilder.append("</ROW>");
            }
            strBuilder.append("</ROWS>");
            return strBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ReplaceSpecific(Object str) {
        if (str != null) if (!this.isXMLValid(str.toString()))
            return str.toString().replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
        else return str.toString();
        return "";
    }

    public boolean isXMLValid(String string) {
        try {
            SAXParserFactory.newInstance().newSAXParser().getXMLReader().parse(new InputSource(new StringReader(string)));
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            return false;
        }
    }

    public ResponseModel selectCMD(Connection conn, String cmdSQL, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        PreparedStatement stm = null;
        ResultSet rs = null;
        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        try {
            stm = conn.prepareStatement(cmdSQL);
            if (listField.size() > 0) {
                for (ParamBean paramBean : listField) {
                    switch (paramBean.getTypeField()) {
                        case VARCHAR:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            break;
                        case INT:
                            stm.setInt(paramBean.getIdx(), Integer.parseInt(paramBean.getValField()));
                            break;
                        case DOUBLE:
                            stm.setDouble(paramBean.getIdx(), Double.parseDouble(paramBean.getValField()));
                            break;
                        case DATE:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrToSQLDate(paramBean.getValField()));
                            break;
                        case DATETIME:
                            stm.setDate(paramBean.getIdx(), Utilities.convertStrtoSQLDateTime(paramBean.getValField()));
                            break;
                        default:
                            stm.setString(paramBean.getIdx(), paramBean.getValField());
                            break;
                    }
                }
            }
            stm.setQueryTimeout(60);
            rs = stm.executeQuery();
            List<Object> list4Map;
            if(!rs.isBeforeFirst()){
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.NO_DATA.label).errorDesc("NO DATA").build();
            }
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    if (map.containsKey(rsmd.getColumnLabel(i))) {
                        list4Map = map.get(rsmd.getColumnLabel(i));
                    } else {
                        list4Map = new ArrayList<Object>();

                    }
                    list4Map.add(rs.getObject(i));
                    map.put(rsmd.getColumnLabel(i), list4Map);
                }
            }
        } catch (SQLTimeoutException sqlEx) {
            log.error("Exception execPackage: " + sqlEx.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception execPackage: " + sqlEx.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("EXCEPTION selectCMD: " + ex.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("EXCEPTION selectCMD: " + ex.getMessage()).build();
        } finally {
            // ConnectionDB.closeAll(conn, stm, rs);
        }
        responseModel = ResponseModel.builder().resCode(resCode).data(map).build();
        return responseModel;
    }

    public void processBatch(List<String> listSql, FcubsErrorDTO fcubsError) {
        List<String> subList = new ArrayList<>();
        for (String s : listSql) {
            if (s != null) {
                subList.add(s);
            }
            if (subList.size() >= Integer.parseInt(Constants.MAX_BATCH)) {
                importList(subList, fcubsError);
                subList.clear();
            }
            // run the remain SQL in file
        }
        if (!subList.isEmpty()) {
            importList(subList, fcubsError);
        }
    }

    private void importList(List<String> subList, FcubsErrorDTO fcubsError) {
        // Connection conn = ConnectionDB.openESB();
        Connection conn = new OracleConnectionWrapper();
        Statement stm = null;
        int kq = 0;
        try {
            stm = conn.createStatement();
            conn.setAutoCommit(false);
            for (String sql : subList) {
                if (sql.trim().length() > 0) {
                    stm.addBatch(sql);
                }
            }
            int[] executeBatch = stm.executeBatch();
            conn.commit();
            kq = executeBatch.length;
            fcubsError.setECODE("0");
            log.error(String.valueOf(kq));
        } catch (SQLException e) {
            e.printStackTrace();
            fcubsError.setECODE("99");

            log.error("BATCH EXCEPTION" + e.getMessage());
            StringBuilder bu = new StringBuilder();
            for (String sql : subList) {
                bu.append(sql);
                bu.append("#");
            }
            log.error(bu.toString());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
//			ESBAction esb = new ESBAction();
//			esb.loadErrorDesc(fcubsError);
//			ConnectionDB.closeAll(conn, stm);
        }
    }
}
