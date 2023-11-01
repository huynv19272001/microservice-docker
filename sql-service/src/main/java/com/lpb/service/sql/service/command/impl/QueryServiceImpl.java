package com.lpb.service.sql.service.command.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.service.command.IQueryService;
import com.lpb.service.sql.service.command.dao.ESBQueryDAO;
import com.lpb.service.sql.utils.constants.EnumService;
import com.lpb.service.sql.model.FcubsErrorDTO;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.model.ServiceDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class QueryServiceImpl implements IQueryService {
    @Override
    public QueryVO loadConnectorQuery(String hasRole, String serviceId, FcubsErrorDTO fcubsError) {
        ESBQueryDAO dao = new ESBQueryDAO();
        return dao.loadConnectorQuery(hasRole, serviceId, fcubsError);
    }

    @Override
    public EnumService loadTypeService(String serviceId, FcubsErrorDTO fcubsError) {
        ESBQueryDAO dao = new ESBQueryDAO();
        return dao.loadTypeTransaction(serviceId, fcubsError);
    }

    @Override
    public ResponseModel loadSQLInfo(Connection conn, String hasRole, ServiceDTO serviceDto) {
        ESBQueryDAO dao = new ESBQueryDAO();
        return dao.loadSQLInfo(conn, hasRole, serviceDto);
    }

    @Override
    public QueryVO loadConnectorQuery(String hasRole, ServiceDTO serviceDto, FcubsErrorDTO fcubsError) {
        ESBQueryDAO dao = new ESBQueryDAO();
        return dao.loadConProduct(hasRole, serviceDto, fcubsError);
    }

    @Override
    public LpbResCode loadErrorDesc(Connection conn, LpbResCode resCode) {
        ESBQueryDAO dao = new ESBQueryDAO();
        return dao.loadErrorDesc(conn, resCode);
    }
}
