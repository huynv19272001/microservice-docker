package com.lpb.napas.ecom.controller;

import com.lpb.napas.ecom.common.ExceptionPurchase;
import com.lpb.napas.ecom.dto.PurchaseRequest;
import com.lpb.napas.ecom.process.IInitPurchaseResponse;
import com.lpb.napas.ecom.process.IPurchaseProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class PurchaseController {
    @Autowired
    IPurchaseProcess iPurchaseProcess;

    @Autowired
    IInitPurchaseResponse IInitPurchaseResponse;

    @Operation(description = "API Verify Purchase là quá trình thực hiện thanh toán")
    @ApiResponses({
        @ApiResponse(responseCode = "0", description = "Successful", content = @Content),
        @ApiResponse(responseCode = "1", description = "Invalid request", content = @Content),
        @ApiResponse(responseCode = "2", description = "Wrong partner password", content = @Content),
        @ApiResponse(responseCode = "3", description = "Expired transaction", content = @Content),
        @ApiResponse(responseCode = "4", description = "Wrong signature", content = @Content),
        @ApiResponse(responseCode = "5", description = "Transaction does not exist", content = @Content),
        @ApiResponse(responseCode = "6", description = "Invalid transaction", content = @Content),
        @ApiResponse(responseCode = "7", description = "Invalid transaction status", content = @Content),
        @ApiResponse(responseCode = "8", description = "Duplicate transaction", content = @Content),
        @ApiResponse(responseCode = "9", description = "Wrong card info", content = @Content),
        @ApiResponse(responseCode = "10", description = "Wrong card number/account number", content = @Content),
        @ApiResponse(responseCode = "11", description = "Wrong name on card/account name", content = @Content),
        @ApiResponse(responseCode = "12", description = "Wrong card expiry", content = @Content),
        @ApiResponse(responseCode = "13", description = "Wrong issue date", content = @Content),
        @ApiResponse(responseCode = "14", description = "Card/Account is invalid", content = @Content),
        @ApiResponse(responseCode = "15", description = "Card/Account is locked", content = @Content),
        @ApiResponse(responseCode = "16", description = "Card/Account haven’t been registered for online payment", content = @Content),
        @ApiResponse(responseCode = "17", description = "Transaction amount exceeds one transaction limit", content = @Content),
        @ApiResponse(responseCode = "18", description = "Transaction amount exceeds limit per day", content = @Content),
        @ApiResponse(responseCode = "19", description = "Card/Account has insufficient balance", content = @Content),
        @ApiResponse(responseCode = "20", description = "Wrong OTP", content = @Content),
        @ApiResponse(responseCode = "21", description = "Expired OTP", content = @Content),
        @ApiResponse(responseCode = "97", description = "Not allowed transaction", content = @Content),
        @ApiResponse(responseCode = "98", description = "Other error", content = @Content),
        @ApiResponse(responseCode = "99", description = "Internal error", content = @Content)
    })
    @PostMapping(value = "/api/v1/purchase")
    public ResponseEntity<?> purchase(@RequestBody PurchaseRequest purchaseRequest) throws Exception {
        try {
            return new ResponseEntity<>(iPurchaseProcess.initPurchaseResponse(purchaseRequest), HttpStatus.OK);
        } catch (ExceptionPurchase e) {
            return new ResponseEntity<>(IInitPurchaseResponse.initPurchaseResponse
                (e.getPurchaseRequest(), e.getInitPurchaseRequest(),
                    e.getDataPurchaseRequest(), e.getAppId()), HttpStatus.BAD_REQUEST);
        }
    }
}
