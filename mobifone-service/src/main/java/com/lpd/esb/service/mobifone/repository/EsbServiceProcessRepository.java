package com.lpd.esb.service.mobifone.repository;

import com.lpd.esb.service.mobifone.model.entity.EsbServiceProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EsbServiceProcessRepository extends JpaRepository<EsbServiceProcess, String> {
    @Modifying
    @Query(value = "UPDATE EsbServiceProcess e SET e.udf1 = :expDate, e.udf2 = :tokenKey " +
        "WHERE e.serviceId = :serviceId and e.productCode = :productCode")
    void updateToken(@Param("expDate") String expDate, @Param("tokenKey") String tokenKey,
                     @Param("serviceId") String serviceId, @Param("productCode") String productCode);
}
