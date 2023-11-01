package com.lpb.esb.service.tct.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.service.DanhmucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-03-15
 */
@RestController
@RequestMapping(value = "danh-muc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DanhmucController {
    @Autowired
    DanhmucService danhmucService;

    @RequestMapping(value = "truy-van", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> coQuanThu(@RequestBody RequestData requestData) {
        ResponseModel responseModel = danhmucService.truyVanDanhMuc(requestData);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }


}
