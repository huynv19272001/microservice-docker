package com.esb.card.rest;

import com.esb.card.dto.getaccountlist.GetAccountListREQDTO;
import com.esb.card.dto.getavlbalance.GetAvlBalanceREQDTO;
import com.esb.card.service.IAccountService;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;

    @Operation(description = "Lấy thông tin của khách hàng")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "11", description = "Field Input không hợp lệ", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-account-list")
    public ResponseEntity<?> getAcccountList(@RequestBody GetAccountListREQDTO getAccountListDTO) throws Exception {
        try {
            ResponseModel responseModel = iAccountService.getAccountList(getAccountListDTO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy thông tin số dư của khách hàng")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "11", description = "Field Input không hợp lệ", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-avl-balance")
    public ResponseEntity<?> getAvlBalance(@RequestBody GetAvlBalanceREQDTO getAvlBalanceDTO) {
        try {
            ResponseModel responseModel = iAccountService.getAvlBalance(getAvlBalanceDTO);
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
