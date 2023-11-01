package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbUserPermission;
import com.lpb.esb.service.config.service.EsbUserPermissionService;
import com.lpb.esb.service.config.service.EsbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tudv1 on 2021-11-17
 */
@RestController
@RequestMapping(value = "account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    @Autowired
    EsbUserService esbUserService;
    @Autowired
    EsbUserPermissionService esbUserPermissionService;

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getAllAccount(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber
        , @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        if (pageNumber <= 0) pageNumber = 1;
        if (pageSize <= 0) pageSize = 20;
        ResponseModel responseModel = esbUserService.getAllUser(pageNumber, pageSize);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get-all-user-role", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getAllUserRole(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber
        , @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        if (pageNumber <= 0) pageNumber = 1;
        if (pageSize <= 0) pageSize = 20;
        ResponseModel responseModel = esbUserPermissionService.getAllUserRole(pageNumber, pageSize);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get-user-role/{username}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getUserRole(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber
        , @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
        , @PathVariable(value = "username") String username
    ) {
        if (pageNumber <= 0) pageNumber = 1;
        if (pageSize <= 0) pageSize = 20;
        ResponseModel responseModel = esbUserPermissionService.getAllRoleByUser(username, pageNumber, pageSize);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get-detail-user-role/{username}/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getDetailUserRole(@PathVariable(value = "username") String username
        , @PathVariable(value = "roleId") String roleId
    ) {
        ResponseModel responseModel = esbUserPermissionService.getUserRoleDetail(username, roleId);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "save-user-role", method = {RequestMethod.POST, RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> saveUserRole(@RequestBody EsbUserPermission esbUserPermission) {
        ResponseModel responseModel = esbUserPermissionService.saveUserRole(esbUserPermission);
        if (responseModel.getResCode().getErrorCode().equalsIgnoreCase(EsbErrorCode.SUCCESS.label)) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
