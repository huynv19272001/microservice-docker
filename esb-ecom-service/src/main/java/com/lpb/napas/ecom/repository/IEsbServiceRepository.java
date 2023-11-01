package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.EsbService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEsbServiceRepository extends JpaRepository<EsbService, String> {
    @Query("SELECT u FROM EsbService u WHERE u.serviceId = :serviceId")
    EsbService getEsbServiceByServiceId(@Param("serviceId") String userStatus);
}
