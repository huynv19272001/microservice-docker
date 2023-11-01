package com.esb.transaction.rest;

import com.esb.transaction.dto.*;
import com.esb.transaction.service.ITransactionService;
import com.esb.transaction.utils.ESBUtils;
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

@Log4j2
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @Operation(description = "Lấy thông tin TransactionPost")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content)
    })
    @GetMapping(value = "{app_id}")
    public ResponseEntity<?> initTransaction(@PathVariable("app_id") String appId) {
        try {
            ResponseModel responseModel = transactionService.loadTransactionPost(appId);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "API tiến hành chuyển tiền")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content),
        @ApiResponse(responseCode = "44", description = "Tài khoản số dư không đủ", content = @Content),
        @ApiResponse(responseCode = "45", description = "Duplicate message", content = @Content),
        @ApiResponse(responseCode = "46", description = "Số tài khoản không đúng", content = @Content)
    })
    @PostMapping(value = "upload-transfer-jrn")
    public ResponseEntity<?> uploadTransferJRN(@RequestBody UploadTransferJRNDTO uploadTransferIO) {
        try {
            ResponseModel responseModel = transactionService.uploadTransferJRN(uploadTransferIO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @author Trung.Nguyen
     * @date 15-Sep-2022
     * */
    @PostMapping(value = "upload-transfer-citad")
    public ResponseEntity<?> uploadTransferCitad(@RequestBody UploadTransferJRNDTO uploadTransferIO) {
        try {
            ResponseModel responseModel = transactionService.uploadTransferCitad(uploadTransferIO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "API khởi tạo giao dịch")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content),
        @ApiResponse(responseCode = "90", description = "Timeout", content = @Content)
    })
    @PostMapping(value = "init-transaction")
    public ResponseEntity<?> initTransaction(@RequestBody TransactionDTO transInfo) {
        try {
            log.info(transInfo.getTransactionInfo().getTransactionId() + " InitTransaction "
                + transInfo.getTransactionInfo().getTrnBrn() + " - "
                + transInfo.getTransactionInfo().getTrnDesc() + " - "
                + transInfo.getTransactionInfo().getTransactionId() + " - "
                + ESBUtils.createXmlCustomer(transInfo.getTransactionInfo().getCustomerNo()) + " - "
                + ESBUtils.createXmlPartner(transInfo.getPartnerInfo()) + " - "
                + ESBUtils.createXmlServiceDTO(transInfo.getService()) + " - "
                + ESBUtils.createXmlPostInfo(transInfo.getPostInfo()));

            ResponseModel responseModel = transactionService.initTransaction(transInfo.getTransactionInfo().getUserId(),
                transInfo.getTransactionInfo().getTrnBrn(),
                transInfo.getTransactionInfo().getTrnDesc(),
                transInfo.getTransactionInfo().getTransactionId(),
                ESBUtils.createXmlCustomer(transInfo.getTransactionInfo().getCustomerNo()),
                ESBUtils.createXmlPartner(transInfo.getPartnerInfo()),
                ESBUtils.createXmlServiceDTO(transInfo.getService()),
                ESBUtils.createXmlPostInfo(transInfo.getPostInfo()));

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
