package com.esb.card.respository;

import com.esb.card.model.EsbCardCoreUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsbCardCoreUserInfoRepository extends JpaRepository<EsbCardCoreUserInfo, String> {
}
