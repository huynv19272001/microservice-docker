package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbServiceProcess;
import com.lpb.esb.service.config.model.entities.EsbServiceProcessEntity;
import com.lpb.esb.service.config.service.EsbConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by tudv1 on 2021-07-15
 */
@RestController
@RequestMapping(value = "service/process", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {
    @Autowired
    EsbConfigService esbConfigService;

    @RequestMapping(value = "set-code", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<ListModel<String>>> getListServiceProduct() {
        Set<String> list = esbConfigService.getListServiceProductName();
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Get list service product code success")
            .build();
        ResponseModel<ListModel<String>> responseModel = ResponseModel.<ListModel<String>>builder()
            .resCode(resCode)
            .data(new ListModel<>(list))
            .build();

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "{productCode}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<ListModel<EsbServiceProcess>>> getListServiceProduct(
        @PathVariable(value = "productCode") String productCode
    ) {
        List<EsbServiceProcessEntity> list = esbConfigService.findServiceProcessByProductCode(productCode);
        List<EsbServiceProcess> listRes = PageUtils.copyBeans(list, EsbServiceProcess.class);
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Get list service product code success")
            .build();
        ResponseModel<ListModel<EsbServiceProcess>> responseModel = ResponseModel.<ListModel<EsbServiceProcess>>builder()
            .resCode(resCode)
            .data(new ListModel<>(listRes))
            .build();

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "{serviceId}/{productCode}/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<EsbServiceProcess>> findServiceProcess(
        @PathVariable(value = "serviceId") String serviceId
        , @PathVariable(value = "productCode") String productCode
        , @PathVariable(value = "roleId") String roleId
    ) {

        EsbServiceProcessEntity esbServiceProcessEntity = esbConfigService.findServiceProcess(productCode, serviceId, roleId);
        if (esbServiceProcessEntity == null) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Data not found")
                .build();
            ResponseModel<EsbServiceProcess> responseModel = ResponseModel.<EsbServiceProcess>builder()
                .resCode(resCode)
                .build();

            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            EsbServiceProcess esbServiceProcess = new EsbServiceProcess();
            BeanUtils.copyProperties(esbServiceProcessEntity, esbServiceProcess);
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Find data success for product code " + productCode)
                .build();
            ResponseModel<EsbServiceProcess> responseModel = ResponseModel.<EsbServiceProcess>builder()
                .resCode(resCode)
                .data(esbServiceProcess)
                .build();
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseModel<EsbServiceProcess>> modifyServiceProcess(
        HttpServletRequest request
        , @RequestBody EsbServiceProcess body
    ) {
        String method = request.getMethod();
        EsbServiceProcessEntity esbServiceProcessEntity = null;

        // PUT: update
        if (method.equals(RequestMethod.PUT.toString())) {
            esbServiceProcessEntity = esbConfigService.findServiceProcess(body.getProductCode(), body.getServiceId(), body.getRoleId());
            if (esbServiceProcessEntity == null) {
                LpbResCode resCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("Data not found")
                    .build();
                ResponseModel<EsbServiceProcess> responseModel = ResponseModel.<EsbServiceProcess>builder()
                    .resCode(resCode)
                    .build();

                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                esbServiceProcessEntity.setConnectorUrl(body.getConnectorUrl());
                esbServiceProcessEntity.setUrlApi(body.getUrlApi());
                if (body.getEffDate() != null) {
                    esbServiceProcessEntity.setEffDate(body.getEffDate());
                }
            }
        } else {    // POST: create
            esbServiceProcessEntity = new EsbServiceProcessEntity();
            BeanUtils.copyProperties(body, esbServiceProcessEntity);
        }

        EsbServiceProcessEntity res = esbConfigService.saveServiceProcess(esbServiceProcessEntity);
//        EsbServiceProcessEntity res = null;
        if (res != null) {
            EsbServiceProcess bean = new EsbServiceProcess();
            BeanUtils.copyProperties(esbServiceProcessEntity, bean);
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Save data success for product code " + body.getProductCode())
                .build();
            ResponseModel<EsbServiceProcess> responseModel = ResponseModel.<EsbServiceProcess>builder()
                .resCode(resCode)
                .data(bean)
                .build();
            return ResponseEntity.ok(responseModel);
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Save data failed")
                .build();
            ResponseModel<EsbServiceProcess> responseModel = ResponseModel.<EsbServiceProcess>builder()
                .resCode(resCode)
                .build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }
}
