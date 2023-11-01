package com.lpb.service.sql.service.command.impl;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.service.command.ICommandService;
import com.lpb.service.sql.model.FcubsErrorDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class ESBCommandService {
    private ICommandService iCommandService;

    public ESBCommandService() {
        this.iCommandService = new CommandServiceImpl();
    }

    public boolean dmlCommand(Connection conn, String cmdSQL, List<ParamBean> listField, FcubsErrorDTO fcubsError) {
        return iCommandService.dmlCommand(conn, cmdSQL, listField, fcubsError);
    }

    public ResponseModel selectCMD(Connection conn, String cmdSQL, List<ParamBean> listField) {
        return iCommandService.selectCMD(conn, cmdSQL, listField);
    }

    public ResponseModel execPackage(Connection conn, String funcName, List<ParamBean> listField) {
        return iCommandService.execPackage(conn, funcName, listField);
    }

    public ResponseModel execPackageMySQL(Connection conn, String funcName, List<ParamBean> listField) {
        return iCommandService.execPackageMySQL(conn, funcName, listField);
    }

    public void executeBatch(List<String> listSQL, FcubsErrorDTO fcubsError) {
        iCommandService.processBatch(listSQL, fcubsError);
    }
}
