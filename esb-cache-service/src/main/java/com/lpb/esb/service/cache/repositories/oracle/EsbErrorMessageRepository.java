package com.lpb.esb.service.cache.repositories.oracle;

import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import com.lpb.esb.service.cache.model.oracle.id.EsbErrorMessageID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-08-26
 */
@Repository
public interface EsbErrorMessageRepository extends JpaRepository<EsbErrorMessageEntity, EsbErrorMessageID> {
}
