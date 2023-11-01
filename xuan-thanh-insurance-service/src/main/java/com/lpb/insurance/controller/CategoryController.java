package com.lpb.insurance.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> category(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = categoryService.category(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
