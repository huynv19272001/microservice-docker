package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.dto.OtpDTO;
import com.lpb.esb.service.config.model.entities.UserBypassOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cuongnm10 on 2022-06-29
 */
@Repository
public interface UserBypassOtpRepository extends JpaRepository<UserBypassOtpEntity, OtpDTO> {
    List<UserBypassOtpEntity> findAll();

    UserBypassOtpEntity findByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_BYPASS_OTP t SET t.RECORD_STAT = ?1, t.UDF1 = ?2, t.UDF2 = ?3 WHERE t.USER_ID = ?4 ",
        nativeQuery = true)
    void updateUser(String recordStat, String date, String checkerName, String userId);
}
