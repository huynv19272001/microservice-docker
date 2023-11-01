package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbUserPermission;
import com.lpb.esb.service.config.model.entities.EsbPermissionEntity;
import com.lpb.esb.service.config.model.entities.EsbUserEntity;
import com.lpb.esb.service.config.model.entities.EsbUserPermissionEntity;
import com.lpb.esb.service.config.model.entities.id.EsbUserPermissionID;
import com.lpb.esb.service.config.repository.EsbPermissionRepository;
import com.lpb.esb.service.config.repository.EsbUserPermissionRepository;
import com.lpb.esb.service.config.repository.EsbUserRepository;
import com.lpb.esb.service.config.service.EsbUserPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tudv1 on 2021-11-17
 */
@Service
public class EsbUserPermissionServiceImpl implements EsbUserPermissionService {
    @Autowired
    EsbUserPermissionRepository esbUserPermissionRepository;
    @Autowired
    EsbPermissionRepository esbPermissionRepository;
    @Autowired
    EsbUserRepository esbUserRepository;

    @Override
    public ResponseModel getAllUserRole(int pageNumber, int pageSize) {
        Page<EsbUserPermissionEntity> page = esbUserPermissionRepository.findAll(PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("makerDt"))));

        ListModel<EsbUserPermission> listModel = PageUtils.initPageModel(page, pageNumber, pageSize, EsbUserPermission.class);
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
    public ResponseModel getUserRoleDetail(String username, String roleId) {
        EsbUserPermissionID id = EsbUserPermissionID.builder()
            .username(username)
            .roleId(roleId)
            .build();

        EsbUserPermissionEntity entity = esbUserPermissionRepository.findById(id).orElse(null);
        if (entity == null) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.NO_DATA.label)
                .errorDesc("No data found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(null)
                .build();
            return responseModel;
        } else {
            EsbUserPermission esbUserPermission = new EsbUserPermission();
            BeanUtils.copyProperties(entity, esbUserPermission);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(esbUserPermission)
                .build();
            return responseModel;
        }
    }

    @Override
    public ResponseModel getAllRoleByUser(String username, int pageNumber, int pageSize) {
        Page<EsbUserPermissionEntity> page = esbUserPermissionRepository.findAllByUsername(username, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("makerDt"))));

        ListModel<EsbUserPermission> listModel = PageUtils.initPageModel(page, pageNumber, pageSize, EsbUserPermission.class);
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
    public ResponseModel saveUserRole(EsbUserPermission esbUserPermission) {
        List<EsbPermissionEntity> listRole = esbPermissionRepository.findAllByRoleId(esbUserPermission.getRoleId());
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Succcess")
            .build();
        if (listRole.size() <= 0) {
            lpbResCode.setErrorCode(EsbErrorCode.NO_DATA.label);
        } else {
            EsbUserEntity user = esbUserRepository.findById(esbUserPermission.getUsername()).orElse(null);
            if (user == null) {
                lpbResCode.setErrorCode(EsbErrorCode.NO_DATA.label);
            }
        }

        if (EsbErrorCode.SUCCESS.label.equalsIgnoreCase(lpbResCode.getErrorCode())) {
            EsbUserPermissionID id = EsbUserPermissionID.builder()
                .username(esbUserPermission.getUsername())
                .roleId(esbUserPermission.getRoleId())
                .build();
            EsbUserPermissionEntity entity = esbUserPermissionRepository.findById(id).orElse(new EsbUserPermissionEntity());
            BeanUtils.copyProperties(esbUserPermission, entity);
            esbUserPermissionRepository.save(entity);
        } else {
            lpbResCode.setErrorDesc("No data found");
        }
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(esbUserPermission)
            .build();
        return responseModel;
    }
}
