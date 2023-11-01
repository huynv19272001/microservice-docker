package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbUser;
import com.lpb.esb.service.config.model.entities.EsbUserEntity;
import com.lpb.esb.service.config.repository.EsbUserRepository;
import com.lpb.esb.service.config.service.EsbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-11-17
 */
@Service
public class EsbUserServiceImpl implements EsbUserService {
    @Autowired
    EsbUserRepository esbUserRepository;

    @Override
    public ResponseModel getAllUser(int pageNumber, int pageSize) {
        Page<EsbUserEntity> page = esbUserRepository.findAll(PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("markerDt"))));

        ListModel<EsbUser> listModel = PageUtils.initPageModel(page, pageNumber, pageSize, EsbUser.class);
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
}
