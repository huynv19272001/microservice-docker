package com.lpb.esb.service.config.service;

import com.lpb.esb.service.common.model.response.ResponseModel;

/**
 * Created by tudv1 on 2021-11-16
 */
public interface EsbPermissionService {
    ResponseModel findAll(int pageSize, int pageNumber);
    ResponseModel findAllRoleId();
    ResponseModel getServiceInfo(String serviceId, String productCode, String hasRole);
    ResponseModel getServiceInfo(String serviceId, String productCode);
}
