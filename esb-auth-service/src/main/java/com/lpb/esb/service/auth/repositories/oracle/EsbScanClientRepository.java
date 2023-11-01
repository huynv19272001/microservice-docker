package com.lpb.esb.service.auth.repositories.oracle;

import com.lpb.esb.service.auth.model.oracle.EsbScanClient;
import com.lpb.esb.service.auth.model.oracle.id.EsbScanClientID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-07-13
 */
@Repository
public interface EsbScanClientRepository extends JpaRepository<EsbScanClient, EsbScanClientID> {
    List<EsbScanClient> findAllByScanIp(String scanIp);
}
