package com.lpb.esb.service.query.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.query.model.EsbRequestDTO;
import com.lpb.esb.service.query.service.QueryService;
import com.lpb.esb.service.query.service.QueryServiceV2;
import com.lpb.esb.service.query.service.QueryServiceV3;
import com.lpb.esb.service.query.service.impl.TCTServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "query", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class QueryController {
    @Autowired
    QueryService queryService;

    @Autowired
    QueryServiceV3 queryServiceV1;

    @Autowired
    QueryServiceV2 queryServiceV2;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ResponseEntity<?> search(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = queryService.search(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "v3/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchV2(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = queryServiceV1.search(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "v2/search", method = RequestMethod.POST)
    public ResponseEntity<?> search_v2(@RequestBody EsbReqDTO esbReqDTO) {
        log.info(esbReqDTO);
        ResponseModel responseModel = queryServiceV2.search(esbReqDTO);
//        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
        return ResponseEntity.ok(responseModel);
//        } else {
//            return ResponseEntity.badRequest().body(responseModel);
//        }
    }

    public QueryService loadServiceFactory(EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO.getHeader().getServiceId());
        switch (esbRequestDTO.getHeader().getServiceId()) {
            case "580000":
                return new TCTServiceImpl();
            default:
                return null;
        }
    }
}
