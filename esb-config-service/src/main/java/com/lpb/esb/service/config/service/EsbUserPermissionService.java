package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.config.model.dto.EsbUserPermission;

/**
 * Created by tudv1 on 2021-11-17
 */
public interface EsbUserPermissionService {
    ResponseModel getAllUserRole(int pageNumber, int pageSize);

    ResponseModel getUserRoleDetail(String username, String roleId);

    ResponseModel getAllRoleByUser(String username, int pageNumber, int pageSize);

    ResponseModel saveUserRole(EsbUserPermission esbUserPermission);
}
