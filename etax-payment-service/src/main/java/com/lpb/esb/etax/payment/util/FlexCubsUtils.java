/**
 * @author Trung.Nguyen
 * @date 05-May-2022
 * */
package com.lpb.esb.etax.payment.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FlexCubsUtils {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	public String getMsgIdByDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
			String msgId = sdf.format(new Timestamp(System.currentTimeMillis()));
			return msgId;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a string that represents a date.
	 * <p>
	 * Formats: <blockquote>
	 *
	 * <pre>
	 * dd/MM/yyyy
	 * dd/MM/yyyy HH:mm:ss
	 * dd-MM-yyyy
	 * dd-MM-yyyy HH:mm:ss
	 * yyyy/MM/dd
	 * yyyy-MM-dd
	 * yyyyMMdd
	 * yyyyMMddHHmmss
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * @param format
	 */
	public String getTransDate(String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String transDate = sdf.format(new Timestamp(System.currentTimeMillis()));
			return transDate;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

//	public String checkCutOffTime(String serviceId) {
//		SimpleJdbcCall jdbcCall;
//		String result = "error";
//		try {
//			jdbcCall = new SimpleJdbcCall(jdbcTemplate)
//	                		.withCatalogName("PKG_ESB_SERVICE")
//	                		.withFunctionName("Fn_checkCOT")
//	                		.withReturnValue();
//			SqlParameterSource input = new MapSqlParameterSource()
//	                .addValue("p_serviceID", serviceId);
////			result = jdbcCall.executeFunction(String.class, params);
//			Map<String, Object> output = jdbcCall.execute(input);
//			for(String key : output.keySet()) {
//				log.info(key + "=" + output.get(key));
//			}
//			result = (String)output.get("return");
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return result;
//	}

    public String checkCutOffTime(String serviceId) {
        SimpleJdbcCall jdbcCall;
        String result = "error";
        try {
            log.info("Start calling - PKG_ESB_SERVICE.Fn_checkCOT ...");

            List<SqlParameter> parameters = Arrays.asList(
                new SqlOutParameter("return", Types.VARCHAR), 				// this must be added to get return value
                new SqlParameter("serviceId", Types.VARCHAR));
            log.info("Set parameters...");
            Map<String, Object> out = jdbcTemplate.call(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                    // ordering the range of parameters by symbol '?'
                    CallableStatement callableStatement = conn.prepareCall("{? = call PKG_ESB_SERVICE.Fn_checkCOT(?)}");
                    callableStatement.registerOutParameter(1, Types.VARCHAR);
                    callableStatement.setString(2, serviceId);
                    return callableStatement;
                }
            }, parameters);
            // log result of plsql function
            for (String key : out.keySet()) {
                log.info(key + "=" + out.get(key));
            }
            log.info("End calling - PKG_ESB_SERVICE.Fn_checkCOT");

            result = (String) out.get("return");;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
