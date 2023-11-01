package com.lpb.service.sql.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.service.sql.configuration.DBContextHolder;
import com.lpb.service.sql.configuration.DynamicDB;
import com.lpb.service.sql.utils.constants.Constants;
import com.lpb.service.sql.utils.constants.DBTypeEnum;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.service.ISQLService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
@Configurable
@Log4j2
public class SQLServiceImpl implements ISQLService {
    @Override
    public void switchDataSource(QueryVO queryVo) {
        // Load Connection From DB Or Connection Pool
        log.info("Start Connect Database Execute: " + queryVo.getCONNECTOR_NAME());
        switch (queryVo.getCONNECTOR_NAME().toString().trim()) {
            case Constants.CONN_NAME_MAIN:
                DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
                log.info("DBContextHolder: " + DBTypeEnum.MAIN);
                break;
            case Constants.CONN_NAME_ESB:
                DBContextHolder.setCurrentDb(DBTypeEnum.ESB);
                log.info("DBContextHolder: " + DBTypeEnum.ESB);
                break;
            case Constants.CONN_NAME_CORE:
                DBContextHolder.setCurrentDb(DBTypeEnum.CORE);
                log.info("DBContextHolder: " + DBTypeEnum.CORE);
                break;
            case Constants.CONN_NAME_CORE2:
                DBContextHolder.setCurrentDb(DBTypeEnum.CORE2);
                log.info("DBContextHolder: " + DBTypeEnum.CORE2);
                break;
            case Constants.CONN_NAME_LOS:
                DBContextHolder.setCurrentDb(DBTypeEnum.CORE_LOS);
                log.info("DBContextHolder: " + DBTypeEnum.CORE_LOS);
                break;
            case Constants.CONN_NAME_DWH:
                DBContextHolder.setCurrentDb(DBTypeEnum.DWH);
                log.info("DBContextHolder: " + DBTypeEnum.DWH);
                break;
            case Constants.CONN_NAME_XHTD:
                DBContextHolder.setCurrentDb(DBTypeEnum.XHTD);
                log.info("DBContextHolder: " + DBTypeEnum.XHTD);
                break;
            case Constants.CONN_NAME_PMDG:
                DBContextHolder.setCurrentDb(DBTypeEnum.PMDG);
                log.info("DBContextHolder: " + DBTypeEnum.PMDG);
                break;
            case Constants.CONN_NAME_WEBLPB:
                DBContextHolder.setCurrentDb(DBTypeEnum.WEBLPB);
                log.info("DBContextHolder: " + DBTypeEnum.WEBLPB);
                break;
            case Constants.CONN_NAME_LV24:
                DBContextHolder.setCurrentDb(DBTypeEnum.LV24);
                log.info("DBContextHolder: " + DBTypeEnum.LV24);
                break;
            case Constants.CONN_NAME_SWIFT:
                DBContextHolder.setCurrentDb(DBTypeEnum.SWIFT);
                log.info("DBContextHolder: " + DBTypeEnum.SWIFT);
                break;
            default:
                DBContextHolder.setCurrentDb(DBTypeEnum.ESB);
                log.info("DBContextHolder: " + DBTypeEnum.OTHER);
                break;
        }
        log.info("End Connect Database Execute");
    }

    @Override
    public Connection switchDataSource2(QueryVO queryVo) {
        log.info("Start Connect Database Execute: " + queryVo.getCONNECTOR_NAME());
        Connection conn = null;
        conn = DynamicDB.openConnectionDB(queryVo.getCONNECTOR_URL(), queryVo.getUDF1(), queryVo.getUDF2());
        log.info("End Connect Database Execute");
        return conn;
    }

    @Override
    public String writeValueAsString(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = "";
        try {
            content = objectMapper.writeValueAsString(data);
            log.info(content);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return content;
    }
}
