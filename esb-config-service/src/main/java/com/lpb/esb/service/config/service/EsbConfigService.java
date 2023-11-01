package com.lpb.esb.service.config.service;

import com.lpb.esb.service.config.model.entities.EsbServiceProcessEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by tudv1 on 2021-07-15
 */
public interface EsbConfigService {
    Set<String> getListServiceProductName();

    EsbServiceProcessEntity findServiceProcess(String productCode, String serviceId, String roleId);

    List<EsbServiceProcessEntity> findServiceProcessByProductCode(String productCode);

    EsbServiceProcessEntity saveServiceProcess(EsbServiceProcessEntity esbServiceProcessEntity);
}
