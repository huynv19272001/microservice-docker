package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.EsbLimitAmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEsbLimitAmtRepository extends JpaRepository<EsbLimitAmt, String> {
    @Query("SELECT a FROM EsbLimitAmt a WHERE a.serviceId = :serviceId")
    List<EsbLimitAmt> getListByServiceId(@Param("serviceId") String serviceId);
}
