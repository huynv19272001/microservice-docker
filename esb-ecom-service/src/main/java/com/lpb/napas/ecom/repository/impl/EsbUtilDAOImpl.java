package com.lpb.napas.ecom.repository.impl;

import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.repository.IEsbUtilDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Log4j2
@Repository
public class EsbUtilDAOImpl implements IEsbUtilDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String loadSequencesTypeMsg(String typeMsg) {
        SimpleJdbcCall jdbcCall;
        String result = null;
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(Constant.PKG_ESB_UTIL)
                    .withFunctionName(Constant.loadSequencesTypeMsg)
                    .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("typeMsg", typeMsg);
            Map results = jdbcCall.execute(paramMap);
            result = (String) results.get("return");
        } catch (Exception e) {
            log.info("Exception loadSequencesTypeMsg: " + e);
            result = null;
        }
        return result;
    }
}
