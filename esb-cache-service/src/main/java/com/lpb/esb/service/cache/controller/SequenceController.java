package com.lpb.esb.service.cache.controller;

import com.lpb.esb.service.cache.service.sequence.SequenceService;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tudv1 on 2021-08-19
 */
@RestController
@RequestMapping(value = "sequence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SequenceController {
    @Autowired
    SequenceService sequenceService;

    @RequestMapping(value = "next/{sequenceName}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getSequenceId(HttpServletRequest request
        , @PathVariable String sequenceName
    ) {
        ResponseModel responseModel = sequenceService.getSequenceNextVal(sequenceName);
        if (responseModel.getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            return ResponseEntity
                .ok()
                .body(responseModel);
        } else {
            return ResponseEntity
                .badRequest()
                .body(responseModel);
        }
    }
}

