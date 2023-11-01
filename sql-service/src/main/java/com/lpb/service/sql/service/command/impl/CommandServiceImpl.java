package com.lpb.service.sql.service.command.impl;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.service.command.ICommandService;
import com.lpb.service.sql.service.command.dao.ESBCommandDAO;
import com.lpb.service.sql.model.FcubsErrorDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class CommandServiceImpl implements ICommandService {
	private ESBCommandDAO dao;

	public CommandServiceImpl() {
		dao = new ESBCommandDAO();
	}

	@Override
	public boolean dmlCommand(Connection conn, String cmdSQL, List<ParamBean> listField, FcubsErrorDTO fcubsError) {
		return dao.insertOrUpdate(conn, cmdSQL, listField, fcubsError);
	}

	@Override
	public ResponseModel execPackage(Connection conn, String funcName, List<ParamBean> listField) {
		return dao.execPackage(conn, funcName, listField);
	}

	@Override
	public ResponseModel execPackageMySQL(Connection conn, String funcName, List<ParamBean> listField) {
		return dao.execPackageMySQL(conn, funcName, listField);
	}

	@Override
	public ResponseModel selectCMD(Connection conn, String cmdSQL, List<ParamBean> listField) {
		return dao.selectCMD(conn, cmdSQL, listField);
	}

	@Override
	public void processBatch(List<String> listSql, FcubsErrorDTO fcubsError) {
		dao.processBatch(listSql, fcubsError);
	}

}
