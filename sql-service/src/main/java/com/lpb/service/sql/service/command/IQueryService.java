package com.lpb.service.sql.service.command;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.utils.constants.EnumService;
import com.lpb.service.sql.model.FcubsErrorDTO;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.model.ServiceDTO;

import java.sql.Connection;

public interface IQueryService {
    QueryVO loadConnectorQuery(String hasRole, String serviceId, FcubsErrorDTO fcubsError);

    QueryVO loadConnectorQuery(String hasRole, ServiceDTO serviceDto, FcubsErrorDTO fcubsError);

    EnumService loadTypeService(String serviceId, FcubsErrorDTO fcubsError);

    ResponseModel loadSQLInfo(Connection conn, String hasRole, ServiceDTO serviceDto);
    LpbResCode loadErrorDesc(Connection conn, LpbResCode resCode);
}
