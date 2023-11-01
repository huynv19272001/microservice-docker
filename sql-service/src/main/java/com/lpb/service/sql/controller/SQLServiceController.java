package com.lpb.service.sql.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.model.RequestDTO;
import com.lpb.service.sql.process.Process;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class SQLServiceController {
    @Autowired
    Process process;

    @Operation(description = "SQL Service")
    @ApiResponses({@ApiResponse(responseCode = "00", description = "Thành công", content = @Content), @ApiResponse(responseCode = "11", description = "Field Input không hợp lệ", content = @Content), @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)})

    @RequestMapping(value = "execute-process", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> executeProcess(@Valid @NotNull @RequestBody RequestDTO request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(request));

            ResponseModel responseModel = process.executeProcess(request);
            if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
                return ResponseEntity.ok(responseModel);
            } else {
                return ResponseEntity.badRequest().body(responseModel);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
