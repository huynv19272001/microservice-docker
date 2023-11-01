package com.lpb.service.bidv.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.constant.MessageContent;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.ExceptionUtils;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.request.LPBDataRequestCT003;
import com.lpb.service.bidv.process.CT003Process;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Log4j2
public class RequestCT003Controller {
    @Autowired
    CT003Process ct003Process;

    @Operation(description = "Truy vấn bản tin CT003")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Success", content = @Content),
        @ApiResponse(responseCode = "99", description = "System error", content = @Content),
        @ApiResponse(responseCode = "07", description = "Validation error, object too long", content = @Content),
        @ApiResponse(responseCode = "06", description = "Validation error, object cannot be null", content = @Content),
    })
    @PostMapping("/api/v1/results-massage/")
    public ResponseEntity requestCT003(@Valid @NotNull @RequestBody LPBRequest<LPBDataRequestCT003> request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(request);
        log.info(request.getMsgId() + " Request: " + data);
        try {
            return new ResponseEntity<>(ct003Process.requestCT003(request), HttpStatus.OK);
        } catch (ExceptionUtils e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.FAIL.label)
                .errorDesc(e.getMessage())
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            log.info(request.getMsgId() + " - " + e.getMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }
}
