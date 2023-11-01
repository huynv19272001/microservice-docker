package com.lpb.service.sql.service.command.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.service.command.IQueryService;
import com.lpb.service.sql.utils.constants.EnumService;
import com.lpb.service.sql.model.FcubsErrorDTO;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.model.ServiceDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class ESBQueryService {
    private IQueryService iquerySerivce;

    public ESBQueryService() {
        this.iquerySerivce = new QueryServiceImpl();
    }

    @Deprecated
    public ESBQueryService(IQueryService iquerySerivce) {
        this.iquerySerivce = iquerySerivce;
    }

    @Deprecated
    public QueryVO loadConnectorQuery(String hasRole, String serviceId, FcubsErrorDTO fcubsError) {
        return iquerySerivce.loadConnectorQuery(hasRole, serviceId, fcubsError);
    }

    public QueryVO loadConnectorQuery(String hasRole, ServiceDTO serviceDto, FcubsErrorDTO fcubsError) {
        return iquerySerivce.loadConnectorQuery(hasRole, serviceDto, fcubsError);
    }

    public EnumService loadTypeService(String serviceID, FcubsErrorDTO fcubsError) {
        return iquerySerivce.loadTypeService(serviceID, fcubsError);
    }

    public ResponseModel loadSqlInfoService(Connection conn, String hasRole, ServiceDTO serviceDto) {
        return iquerySerivce.loadSQLInfo(conn, hasRole, serviceDto);
    }

    public LpbResCode loadErrorDesc(Connection conn, LpbResCode resCode) {
        return iquerySerivce.loadErrorDesc(conn, resCode);
    }
}
