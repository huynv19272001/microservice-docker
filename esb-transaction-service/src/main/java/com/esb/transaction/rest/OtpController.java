package com.esb.transaction.rest;

import com.esb.transaction.dto.*;
import com.esb.transaction.service.IVerifyOtpService;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
@Log4j2
public class OtpController {
    @Autowired
    private IVerifyOtpService verifyOtpService;

    @Operation(description = "Tạo OTP")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "create")
    public ResponseEntity<?> createOtp(@RequestBody CreateOtpREQDTO createOtpREQ) {
        try {
            ResponseModel responseModel = verifyOtpService.createOtp(createOtpREQ);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Kiểm tra OTP")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "43", description = "OTP sai quá số lần quy định", content = @Content),
        @ApiResponse(responseCode = "42", description = "Sai mã OTP", content = @Content),
        @ApiResponse(responseCode = "41", description = "OTP quá hạn", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "verify")
    public ResponseEntity<?> authenOtp(@RequestBody VerifyOtpREQDTO verifyOtpREQ) {
        try {
            ResponseModel responseModel = verifyOtpService.verifyOtp(verifyOtpREQ);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }
}
