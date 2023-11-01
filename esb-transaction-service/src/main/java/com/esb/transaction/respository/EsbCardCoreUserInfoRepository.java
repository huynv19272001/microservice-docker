package com.esb.transaction.respository;

import com.esb.transaction.model.EsbCardCoreUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsbCardCoreUserInfoRepository extends JpaRepository<EsbCardCoreUserInfo, String> {
}
