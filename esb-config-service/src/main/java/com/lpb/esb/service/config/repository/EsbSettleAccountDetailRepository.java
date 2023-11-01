package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbSettleAccountDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2022-07-08
 */
@Repository
public interface EsbSettleAccountDetailRepository extends JpaRepository<EsbSettleAccountDetailEntity, String> {
    EsbSettleAccountDetailEntity findByAccountIdAndRecordStat(String accountId, String recordStat);

    List<EsbSettleAccountDetailEntity> findByRecordStatAndAccountIdIn(String recordStat, List<String> accountIds);
}
