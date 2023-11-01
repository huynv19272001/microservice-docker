package com.lpb.service.sql.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.sql.model.ColumnDTO;
import com.lpb.service.sql.model.ParamDTO;
import com.lpb.service.sql.model.QueryVO;
import com.lpb.service.sql.model.RequestDTO;

import java.util.List;
import java.util.Map;

public interface IDMLService {
    ResponseModel startExecute(RequestDTO requestDTO);

    ResponseModel activeSQLDynamic(QueryVO queryVo, List<ParamDTO> listParam);

    List<List<ColumnDTO>> convertResult(Map<String, List<Object>> data);
}
