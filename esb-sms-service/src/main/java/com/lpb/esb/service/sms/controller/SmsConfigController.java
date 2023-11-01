package com.lpb.esb.service.sms.controller;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.sms.model.dto.EsbSmsCategoryDTO;
import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import com.lpb.esb.service.sms.service.SmsConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tudv1 on 2021-07-15
 */
@RestController
@RequestMapping(value = "config", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SmsConfigController {
    @Autowired
    SmsConfigService smsConfigService;

    @RequestMapping(value = "category/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<ListModel<String>>> getListCategory() {
        List<String> list = smsConfigService.getListCategory();
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Get list SMS Category success")
            .build();
        ResponseModel<ListModel<String>> responseModel = ResponseModel.<ListModel<String>>builder()
            .resCode(resCode)
            .data(new ListModel<>(list))
            .build();

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "category/{category}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<EsbSmsCategoryDTO>> findCategory(
        @PathVariable(name = "category") String category
    ) {
        EsbSmsCategoryEntity esbSmsCategoryEntity = smsConfigService.findCategory(category);
        if (esbSmsCategoryEntity != null) {
            EsbSmsCategoryDTO esbSmsCategoryDTO = new EsbSmsCategoryDTO();
            BeanUtils.copyProperties(esbSmsCategoryEntity, esbSmsCategoryDTO);
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Get list SMS Category success")
                .build();
            ResponseModel<EsbSmsCategoryDTO> responseModel = ResponseModel.<EsbSmsCategoryDTO>builder()
                .resCode(resCode)
                .data(esbSmsCategoryDTO)
                .build();

            return ResponseEntity.ok(responseModel);
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Data not found code Category " + category)
                .build();
            ResponseModel<EsbSmsCategoryDTO> responseModel = ResponseModel.<EsbSmsCategoryDTO>builder()
                .resCode(resCode)
                .build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "category/save", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseModel<EsbSmsCategoryDTO>> saveSmsCategory(
        HttpServletRequest request
        , @RequestBody EsbSmsCategoryDTO body
    ) {
        String method = request.getMethod();
        EsbSmsCategoryEntity esbSmsCategoryEntity;

        // PUT: update
        if (method.equals(RequestMethod.PUT.toString())) {
            esbSmsCategoryEntity = smsConfigService.findCategory(body.getCategory());
            if (esbSmsCategoryEntity == null) {
                LpbResCode resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("Data not found")
                    .build();
                ResponseModel<EsbSmsCategoryDTO> responseModel = ResponseModel.<EsbSmsCategoryDTO>builder()
                    .resCode(resCode)
                    .build();

                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                esbSmsCategoryEntity.setCategoryName(body.getCategoryName());
                esbSmsCategoryEntity.setProductCode(body.getProductCode());
                esbSmsCategoryEntity.setRecordStatus(body.getRecordStatus());
            }
        } else {    // POST: create
            esbSmsCategoryEntity = new EsbSmsCategoryEntity();
            BeanUtils.copyProperties(body, esbSmsCategoryEntity);
        }


        // Do save
        EsbSmsCategoryEntity res = smsConfigService.saveSmsCategory(esbSmsCategoryEntity);
        EsbSmsCategoryDTO dto = new EsbSmsCategoryDTO();
        BeanUtils.copyProperties(esbSmsCategoryEntity, dto);
        if (res != null) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Save data success for product code " + body.getProductCode())
                .build();
            ResponseModel<EsbSmsCategoryDTO> responseModel = ResponseModel.<EsbSmsCategoryDTO>builder()
                .resCode(resCode)
                .data(dto)
                .build();
            return ResponseEntity.ok(responseModel);
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Save data failed")
                .build();
            ResponseModel<EsbSmsCategoryDTO> responseModel = ResponseModel.<EsbSmsCategoryDTO>builder()
                .resCode(resCode)
                .build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }
}
