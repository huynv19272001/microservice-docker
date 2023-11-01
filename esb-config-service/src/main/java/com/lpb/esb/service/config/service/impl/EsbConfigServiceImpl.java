package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.config.model.entities.EsbServiceProcessEntity;
import com.lpb.esb.service.config.model.entities.id.ServiceProcessID;
import com.lpb.esb.service.config.repository.EsbServiceProcessRepository;
import com.lpb.esb.service.config.service.EsbConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tudv1 on 2021-07-15
 */
@Service
@Log4j2
public class EsbConfigServiceImpl implements EsbConfigService {
    //    @Autowired
//    EsbServiceProductRepository esbServiceProductRepository;
    @Autowired
    EsbServiceProcessRepository esbServiceProcessRepository;

    @Override
    public Set<String> getListServiceProductName() {
        List<EsbServiceProcessEntity> list = esbServiceProcessRepository.findAll();
        Set<String> list1 = list.stream()
            .map(x -> x.getProductCode())
            .collect(Collectors.toSet());
        return list1;
    }

    @Override
    public EsbServiceProcessEntity findServiceProcess(String productCode, String serviceId, String roleId) {
        ServiceProcessID serviceProcessID = ServiceProcessID.builder()
            .serviceId(serviceId)
            .productCode(productCode)
            .roleId(roleId)
            .build();
        EsbServiceProcessEntity esbServiceProcessEntity = esbServiceProcessRepository
            .findById(serviceProcessID)
            .orElse(null);
        return esbServiceProcessEntity;
    }

    @Override
    public List<EsbServiceProcessEntity> findServiceProcessByProductCode(String productCode) {
        return esbServiceProcessRepository.findAllByProductCode(productCode);
    }

    @Override
    public EsbServiceProcessEntity saveServiceProcess(EsbServiceProcessEntity esbServiceProcessEntity) {
        try {
            return esbServiceProcessRepository.save(esbServiceProcessEntity);
        } catch (Exception e) {
            log.error("error when save EsbServiveProcess with data [{}]: {}", esbServiceProcessEntity.toString(), e.getCause());
            return null;
        }
    }
}
