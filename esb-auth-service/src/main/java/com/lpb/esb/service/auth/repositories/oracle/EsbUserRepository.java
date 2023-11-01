package com.lpb.esb.service.auth.repositories.oracle;

import com.lpb.esb.service.auth.model.oracle.EsbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-07-13
 */
@Repository
public interface EsbUserRepository extends JpaRepository<EsbUser, String> {
}
