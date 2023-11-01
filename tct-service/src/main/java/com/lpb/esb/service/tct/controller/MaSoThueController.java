package com.lpb.esb.service.tct.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.model.request.dto.EsbTctRequestDTO;
import com.lpb.esb.service.tct.service.CmndService;
import com.lpb.esb.service.tct.service.MstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cuongnm10 on 2022-06-16
 */

@RestController
@RequestMapping(value = "ma-so-thue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaSoThueController {
    @Autowired
    MstService mstService;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> search(@RequestBody EsbTctRequestDTO requestData) throws Exception {

        ResponseModel responseModel = mstService.search(requestData);

            return ResponseEntity.ok(responseModel);
    }
}
