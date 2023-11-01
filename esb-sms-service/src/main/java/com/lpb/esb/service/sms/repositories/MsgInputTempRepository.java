package com.lpb.esb.service.sms.repositories;

import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-24
 */
@Repository
public interface MsgInputTempRepository extends JpaRepository<MsgInputTempEntity, String> {
    List<MsgInputTempEntity> findAllByStatus(String status);
}
