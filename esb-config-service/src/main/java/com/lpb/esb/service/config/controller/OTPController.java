package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.OtpDTO;
import com.lpb.esb.service.config.service.UserBypassOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cuongnm10 on 2022-06-29
 */
@RestController
@RequestMapping(value = "/api/v1/otp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OTPController {
    @Autowired
    UserBypassOTPService userBypassOTPService;


    @RequestMapping(value = "user", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getAllUser(
        @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber
        , @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        ResponseModel responseModel = new ResponseModel();

        responseModel = userBypassOTPService.getAllUser(pageNumber, pageSize);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getDetailUser(
        @PathVariable(value = "userId") String userId
    ) {
        ResponseModel responseModel = new ResponseModel();

        responseModel = userBypassOTPService.getDetailUser(userId);

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    ResponseEntity<ResponseModel> getInsertUser(
        @RequestBody OtpDTO otpDTO
    ) {
        ResponseModel responseModel = new ResponseModel();

        responseModel = userBypassOTPService.save(otpDTO);

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "user", method = RequestMethod.PUT)
    ResponseEntity<ResponseModel> getUpdateUser(
        @RequestBody OtpDTO otpDTO,
        @RequestParam(value = "checkerName", defaultValue = "null") String checkerName
    ) {
        ResponseModel responseModel = new ResponseModel();

        try {
            userBypassOTPService.updateUser(otpDTO, checkerName);
            responseModel = ResponseModel.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Update Succesfully")
                .build();
            return ResponseEntity.ok(responseModel);

        } catch (Exception e) {
            responseModel = ResponseModel.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Update Failed")
                .data(e)
                .build();
            return ResponseEntity.ok(responseModel);
        }
    }

}
