package com.lpd.esb.service.econtract.controller;

import com.lpd.esb.service.econtract.model.RequestDTO;
import com.lpd.esb.service.econtract.model.ResponseDTO;
import com.lpd.esb.service.econtract.service.EcontractService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class EcontractController {
    @Autowired
    EcontractService econtractService;

    @RequestMapping(value = "econtract", method = RequestMethod.POST)
    public ResponseEntity<?> econtract(@RequestBody RequestDTO request) {
        log.info("request: " + request);
        ResponseDTO response= econtractService.econtract(request);
        return ResponseEntity.ok(response);
    }
}
