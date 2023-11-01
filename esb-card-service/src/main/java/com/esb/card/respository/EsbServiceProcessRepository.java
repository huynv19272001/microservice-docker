package com.esb.card.respository;

import com.esb.card.model.EsbServiceProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsbServiceProcessRepository extends JpaRepository<EsbServiceProcess, String> {
    @Query("SELECT a FROM EsbServiceProcess a WHERE a.serviceId = :serviceId AND a.productCode = :productCode")
    List<EsbServiceProcess> getServiceInfo(@Param("serviceId") String serviceId, @Param("productCode") String productCode);
}
