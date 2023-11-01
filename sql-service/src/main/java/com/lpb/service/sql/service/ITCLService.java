package com.lpb.service.sql.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.model.bean.ResultBean;
import com.lpb.service.sql.model.ParamDTO;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.model.RequestDTO;
import com.lpb.service.sql.model.SQLParamDTO;

import java.util.List;

public interface ITCLService {
    ResponseModel startExecute(RequestDTO requestDTO);
    ResponseModel activeSQLDynamic(QueryVO queryVo, List<ParamDTO> listParam);
    void createListBean(List<ParamBean> listBean, List<SQLParamDTO> listVariableDB, List<ParamDTO> listParam);
    ParamBean createParamBean(SQLParamDTO varDB, ParamDTO paramInput);
    ResponseModel transformData(QueryVO queryVo, List<SQLParamDTO> listVariableDB, List<ResultBean> resultBeans);
    List<ParamDTO> convertResult(List<ParamDTO> listParamRequest, List<ParamDTO> listParamResponse);
}
