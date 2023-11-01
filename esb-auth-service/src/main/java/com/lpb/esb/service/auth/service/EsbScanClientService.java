package com.lpb.esb.service.auth.service;

import com.lpb.esb.service.auth.model.oracle.EsbScanClient;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-13
 */
@Service
public interface EsbScanClientService {
    EsbScanClient findEsbScanClientByScanId(String scanId);
}
