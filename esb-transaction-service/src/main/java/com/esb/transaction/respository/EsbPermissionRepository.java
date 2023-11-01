package com.esb.transaction.respository;

import com.esb.transaction.dto.ServiceInfoDTO;
import com.esb.transaction.model.EsbPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsbPermissionRepository extends JpaRepository<EsbPermission, String> {
    //ServiceInfo(String serviceId, String productCode, String urlApi, String connectorURL, String connectPort, String methodAction, String udf1, String udf2, String udf3, String udf4) {
    @Query("SELECT new com.esb.transaction.dto.ServiceInfoDTO(b.serviceId , c.productCode, c.urlApi, c.connectorUrl, c.connectorPort, c.methodAction, c.udf1, c.udf2, c.udf3, c.udf4) " +
            "FROM EsbPermission a JOIN a.esbService b JOIN a.esbServiceProcesses c JOIN c.esbServiceProduct d " +
            "WHERE a.hasRole = :hasRole and b.serviceId = :serviceId and d.productCode = :productCode " +
            "AND (c.udf5='TYPE_DR' or c.udf5 = 'BOTH')")
    List<ServiceInfoDTO> getServiceInfo(@Param("serviceId") String serviceId, @Param("productCode") String productCode, @Param("hasRole") String hasRole);
}
