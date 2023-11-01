package com.lpb.esb.service.tct.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.model.request.dto.EsbTctRequestDTO;
import com.lpb.esb.service.tct.service.ChungTuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-03-02
 */
@RestController
@RequestMapping(value = "chung-tu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChungTuController {
    @Autowired
    ChungTuService chungTuService;

    @RequestMapping(value = "nop", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> nopChungTu(@RequestBody RequestData requestData) {
        ResponseModel responseModel = chungTuService.sendData(requestData);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "huy", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseModel> search(@RequestBody RequestData requestData) {
        ResponseModel responseModel = chungTuService.huyChungTu(requestData);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "truy-van", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> truyvan(@RequestBody EsbTctRequestDTO requestData) throws Exception {
        ResponseModel responseModel = chungTuService.truyvan(requestData);
            return ResponseEntity.ok(responseModel);
    }
}
