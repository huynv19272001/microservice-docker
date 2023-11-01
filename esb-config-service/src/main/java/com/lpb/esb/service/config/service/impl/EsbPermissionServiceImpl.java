package com.lpb.esb.service.config.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CacheUtils;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbPermission;
import com.lpb.esb.service.config.model.entities.EsbPermissionEntity;
import com.lpb.esb.service.config.repository.EsbPermissionRepository;
import com.lpb.esb.service.config.service.EsbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tudv1 on 2021-11-16
 */
@Service
public class EsbPermissionServiceImpl implements EsbPermissionService {

    @Value("${spring.application.name}")
    String module;

    @Autowired
    EsbPermissionRepository esbPermissionRepository;

    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel findAll(int pageSize, int pageNumber) {
        Page<EsbPermissionEntity> page = esbPermissionRepository.findAll(PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("makerDt"))));

        ListModel<EsbPermission> listModel = PageUtils.initPageModel(page, pageNumber, pageSize, EsbPermission.class);
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(listModel)
            .build();

        return responseModel;
    }

    @Override
    public ResponseModel findAllRoleId() {
        List<String> list = esbPermissionRepository.getAllRoleId();
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(list)
            .build();

        return responseModel;
    }

    @Override
    public ResponseModel getServiceInfo(String serviceId, String productCode, String hasRole) {
//        List<EsbPermissionRepository.ServiceInfo> list = new ArrayList<>();
//        String key = serviceId + "@@@" + productCode + "@@@" + hasRole;
//        if (CacheUtils.getCacheValue(restTemplateLB, this.module, key) == null){
//            list = esbPermissionRepository.getServiceInfo(serviceId, productCode, hasRole);
//            CacheUtils.putDataToCache(restTemplateLB, this.module, key, String.valueOf(list), 900l);
//        }
//        else {
//            String data = CacheUtils.getCacheValue(restTemplateLB, this.module, key);
//            System.out.println(data);
//            ObjectMapper objectMapper = new ObjectMapper();
//            TypeFactory typeFactory = objectMapper.getTypeFactory();
//            try {
//                list = objectMapper.readValue(data, typeFactory.constructCollectionType(List.class, EsbPermissionRepository.ServiceInfo.class));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }
        List<EsbPermissionRepository.ServiceInfo> list = esbPermissionRepository.getServiceInfo(serviceId, productCode, hasRole);
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(list)
            .build();

        return responseModel;
    }

    @Override
    public ResponseModel getServiceInfo(String serviceId, String productCode) {
        List<EsbPermissionRepository.ServiceInfo> list = esbPermissionRepository.getServiceInfo(serviceId, productCode);
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(list)
            .build();

        return responseModel;
    }
}
