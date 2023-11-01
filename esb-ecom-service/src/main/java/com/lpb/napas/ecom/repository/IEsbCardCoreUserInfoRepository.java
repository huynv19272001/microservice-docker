package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEsbCardCoreUserInfoRepository extends JpaRepository<EsbCardCoreUserInfo, String> {
    @Query("SELECT a FROM EsbCardCoreUserInfo a WHERE a.username = :username")
    EsbCardCoreUserInfo getEsbCardCoreUserInfoByUsername(@Param("username") String username);
}
