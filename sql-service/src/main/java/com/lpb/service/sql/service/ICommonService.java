package com.lpb.service.sql.service;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.model.ServiceDTO;

import java.util.List;

public interface ICommonService {
    ResponseModel getQuerySQL(String hasRole, ServiceDTO serviceDTO);
    ResponseModel selectCMD(String cmdSQL, List<ParamBean> listField);
    ResponseModel execPackage(String funcName, List<ParamBean> listField);
    ResponseModel execPackageMySQL(String funcName, List<ParamBean> listField);
    LpbResCode loadErrorDesc(LpbResCode resCode);
}
