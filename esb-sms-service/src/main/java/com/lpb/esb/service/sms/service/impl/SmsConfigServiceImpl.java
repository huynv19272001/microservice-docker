package com.lpb.esb.service.sms.service.impl;

import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import com.lpb.esb.service.sms.repositories.EsbSmsCategoryRepository;
import com.lpb.esb.service.sms.service.SmsConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tudv1 on 2021-07-19
 */
@Service
@Log4j2
public class SmsConfigServiceImpl implements SmsConfigService {
    @Autowired
    EsbSmsCategoryRepository esbSmsCategoryRepository;

    @Override
    public List<String> getListCategory() {
        List<String> list = esbSmsCategoryRepository.findAll()
            .stream().map(x -> x.getCategory())
            .collect(Collectors.toSet())
            .stream()
            .sorted()
            .collect(Collectors.toList());

        return list;
    }

    @Override
    public EsbSmsCategoryEntity findCategory(String category) {
        return esbSmsCategoryRepository.findById(category).orElse(null);
    }

    @Override
    public EsbSmsCategoryEntity saveSmsCategory(EsbSmsCategoryEntity esbSmsCategoryEntity) {
        return esbSmsCategoryRepository.save(esbSmsCategoryEntity);
    }
}
