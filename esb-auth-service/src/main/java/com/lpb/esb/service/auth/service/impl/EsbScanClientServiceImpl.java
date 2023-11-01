package com.lpb.esb.service.auth.service.impl;

import com.lpb.esb.service.auth.model.oracle.EsbScanClient;
import com.lpb.esb.service.auth.repositories.oracle.EsbScanClientRepository;
import com.lpb.esb.service.auth.service.EsbScanClientService;
import com.lpb.esb.service.auth.service.TokenCacheService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-13
 */
@Service
public class EsbScanClientServiceImpl implements EsbScanClientService {
    @Autowired
    EsbScanClientRepository esbScanClientRepository;
    @Autowired
    TokenCacheService tokenCacheService;

    @Override
    public EsbScanClient findEsbScanClientByScanId(String scanId) {
        EsbScanClient esbScanClient = esbScanClientRepository
            .findAllByScanIp(scanId)
            .stream()
            .findFirst()
            .orElse(null);

        if (esbScanClient != null) {
            JSONObject jsonObject = new JSONObject()
                .put("username", esbScanClient.getScanIp())
                .put("from_ip", esbScanClient.getRangeIp() + "." + esbScanClient.getFrom())
                .put("to_ip", esbScanClient.getRangeIp() + "." + esbScanClient.getTo());
//            tokenCacheService.pushTokenCache(jsonObject.toString());
        }

        return esbScanClient;
    }
}
