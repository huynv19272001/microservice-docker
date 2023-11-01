package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.StringUtils;
import com.lpb.esb.service.config.service.EsbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-11-16
 */
@RestController
@RequestMapping(value = "permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissionController {

    @Autowired
    EsbPermissionService esbPermissionService;

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getListPermission(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber
        , @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        ResponseModel responseModel = new ResponseModel();
        if (pageNumber <= 0) pageNumber = 1;
        if (pageSize <= 0) pageSize = 20;
        responseModel = esbPermissionService.findAll(pageSize, pageNumber);

        return ResponseEntity.ok(responseModel);
    }


    @RequestMapping(value = "get-all-roleid", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getListRoleId() {
        ResponseModel responseModel = esbPermissionService.findAllRoleId();

        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get-service-info", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getServiceInfo(@RequestParam(value = "serviceId") String serviceId
        , @RequestParam(value = "productCode") String productCode
        , @RequestParam(value = "hasRole", required = false) String hasRole
    ) {
        ResponseModel responseModel;
        if (StringUtils.isNullOrBlank(hasRole)) {
            responseModel = esbPermissionService.getServiceInfo(serviceId, productCode);
        } else {
            responseModel = esbPermissionService.getServiceInfo(serviceId, productCode, hasRole);
        }
        return ResponseEntity.ok(responseModel);
    }
}
