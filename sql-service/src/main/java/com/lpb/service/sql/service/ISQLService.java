package com.lpb.service.sql.service;

import com.lpb.service.sql.model.QueryVO;

import java.sql.Connection;

public interface ISQLService {
    void switchDataSource(QueryVO queryVo);

    Connection switchDataSource2(QueryVO queryVo);

    String writeValueAsString(Object data);
}
