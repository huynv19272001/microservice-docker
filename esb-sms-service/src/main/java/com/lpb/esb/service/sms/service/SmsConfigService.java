package com.lpb.esb.service.sms.service;

import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tudv1 on 2021-07-19
 */
@Service
public interface SmsConfigService {
    List<String> getListCategory();

    EsbSmsCategoryEntity findCategory(String category);

    EsbSmsCategoryEntity saveSmsCategory(EsbSmsCategoryEntity esbSmsCategoryEntity);
}
