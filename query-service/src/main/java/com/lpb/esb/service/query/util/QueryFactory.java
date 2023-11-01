package com.lpb.esb.service.query.util;

import com.lpb.esb.service.query.model.EsbRequestDTO;
import com.lpb.esb.service.query.service.QueryService;
import com.lpb.esb.service.query.service.impl.TCTServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QueryFactory {
    public QueryFactory() {
    }

    public static QueryService getQueryService(EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO.getHeader().getServiceId());
        if ("580000".equals(esbRequestDTO.getHeader().getServiceId())) {
            return new TCTServiceImpl();
        }
        return null;
    }
}
