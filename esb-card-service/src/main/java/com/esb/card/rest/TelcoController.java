package com.esb.card.rest;

import com.esb.card.dto.telcoRequest.EsbTelCoReqDTO;
import com.esb.card.service.TelcoService;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/telco", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TelcoController {
    @Autowired
    TelcoService telcoService;

    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> updateTelco(@RequestBody EsbTelCoReqDTO telcoRequest) {
        ResponseModel responseModel = telcoService.updateTelco(telcoRequest);
        if (!responseModel.getResCode().getErrorCode().equals("ESB-000")) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }
}

