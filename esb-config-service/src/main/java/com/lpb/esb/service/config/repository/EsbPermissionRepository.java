package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbPermissionEntity;
import com.lpb.esb.service.config.model.entities.id.EsbPermissionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-11-16
 */
@Repository
public interface EsbPermissionRepository extends JpaRepository<EsbPermissionEntity, EsbPermissionID> {
    List<EsbPermissionEntity> findAllByRoleId(String roleId);

    @Query("select e.roleId from EsbPermissionEntity e")
    List<String> getAllRoleId();

    @Query(value = "select b.SERVICE_ID as serviceId, c.PRODUCT_CODE as productCode, c.URL_API as urlApi, c.CONNECTOR_URL as connectorURL, c.CONNECTOR_PORT as connectPort,c.METHOD_ACTION  as methodAction"
        + " , c.UDF1 as udf1, c.UDF2 as udf2, c.UDF3 as udf3, c.UDF4 as udf4, c.UDF5 as udf5, c.UDF6 as udf6, c.UDF7 as udf7, c.UDF8 as udf8"
        + " FROM ESB_PERMISSION a JOIN ESB_SERVICE b ON b.service_id = a.service_id"
        + " JOIN ESB_SERVICE_PROCESS c ON a.ROLE_ID = c.ROLE_ID"
        + " JOIN ESB_SERVICE_PRODUCT d on c.PRODUCT_CODE = d.PRODUCT_CODE"
        + " where 1=1 and a.HAS_ROLE = :hasRole and b.SERVICE_ID = :serviceId "
        + " and d.PRODUCT_CODE = :productCode"
        , nativeQuery = true)
    List<ServiceInfo> getServiceInfo(@Param("serviceId") String serviceId, @Param("productCode") String productCode, @Param("hasRole") String hasRole);

    @Query(value = "select b.SERVICE_ID as serviceId, c.PRODUCT_CODE as productCode, c.URL_API as urlApi, c.CONNECTOR_URL as connectorURL, c.CONNECTOR_PORT as connectPort,c.METHOD_ACTION  as methodAction"
        + " , c.UDF1 as udf1, c.UDF2 as udf2, c.UDF3 as udf3, c.UDF4 as udf4, c.UDF5 as udf5, c.UDF6 as udf6, c.UDF7 as udf7, c.UDF8 as udf8"
        + " FROM ESB_PERMISSION a JOIN ESB_SERVICE b ON b.service_id = a.service_id"
        + " JOIN ESB_SERVICE_PROCESS c ON a.ROLE_ID = c.ROLE_ID"
        + " JOIN ESB_SERVICE_PRODUCT d on c.PRODUCT_CODE = d.PRODUCT_CODE"
        + " where 1=1 and b.SERVICE_ID = :serviceId "
        + " and d.PRODUCT_CODE = :productCode"
        , nativeQuery = true)
    List<ServiceInfo> getServiceInfo(@Param("serviceId") String serviceId, @Param("productCode") String productCode);

    interface ServiceInfo {
        String getServiceId();

        String getProductCode();

        String getUrlApi();

        String getConnectorURL();

        String getConnectPort();

        String getMethodAction();

        String getUdf1();

        String getUdf2();

        String getUdf3();

        String getUdf4();

        String getUdf5();

        String getUdf6();

        String getUdf7();

        String getUdf8();

    }
}
