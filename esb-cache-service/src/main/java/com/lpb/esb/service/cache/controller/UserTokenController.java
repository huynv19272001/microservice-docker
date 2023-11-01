package com.lpb.esb.service.cache.controller;

import com.lpb.esb.service.cache.model.redis.UserIpRangeEntity;
import com.lpb.esb.service.cache.service.token.UserIpRangeService;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-11-19
 */
@RestController
@RequestMapping(value = "user/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserTokenController {
    @Autowired
    UserIpRangeService userIpRangeService;

    @RequestMapping(value = "push", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> saveToken(@RequestBody UserIpRangeEntity body) {
        userIpRangeService.addUserIpRangeCacheAsync(body);
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Push cache success")
            .build();

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();

        return ResponseEntity.ok(responseModel);
    }
}
