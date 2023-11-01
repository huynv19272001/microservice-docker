package com.esb.card.rest;

import com.esb.card.dto.unif.customerinfo.UpdateCustomerInfoREQDTO;
import com.esb.card.dto.unif.updatecardstatus.UpdateCardStatusREQDTO;
import com.esb.card.service.ICardUNIFService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card/unif/")
@Log4j2
public class UNIFController {
    @Autowired
    private ICardUNIFService cardService;

    @Operation(description = "Cập nhật thông tin khách hàng")
    @ApiResponses({@ApiResponse(responseCode = "00", description = "Thành công", content = @Content), @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)})
    @PostMapping(value = "update-customer-info")
    public ResponseEntity<?> updateCustomerInfo(@RequestBody UpdateCustomerInfoREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.updateCustomerInfo(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Cập nhật trạng thái thẻ")
    @ApiResponses({@ApiResponse(responseCode = "00", description = "Thành công", content = @Content), @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)})
    @PostMapping(value = "update-card-status")
    public ResponseEntity<?> updateCardStatus(@RequestBody UpdateCardStatusREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.updateCardStatus(infoRequest);
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
