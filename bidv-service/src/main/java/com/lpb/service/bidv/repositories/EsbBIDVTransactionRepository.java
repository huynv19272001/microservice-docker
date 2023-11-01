package com.lpb.service.bidv.repositories;

import com.lpb.service.bidv.model.entities.EsbBIDVTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EsbBIDVTransactionRepository extends JpaRepository<EsbBIDVTransaction, String> {
    @Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL",
        nativeQuery = true)
    Integer getAppMsgID();

    @Query(value = "select b.SERVICE_ID , c.PRODUCT_CODE, c.URL_API, c.CONNECTOR_URL, c.CONNECTOR_PORT, \n" +
        "c.METHOD_ACTION, c.UDF1, c.UDF2, c.UDF3, c.UDF4,c.UDF5, UDF6, c.UDF7, c.UDF8\n" +
        "FROM ESB_PERMISSION a JOIN ESB_SERVICE b ON b.service_id = a.service_id\n" +
        "JOIN ESB_SERVICE_PROCESS c ON a.ROLE_ID = c.ROLE_ID\n" +
        "JOIN ESB_SERVICE_PRODUCT d on c.PRODUCT_CODE = d.PRODUCT_CODE\n" +
        "where 1=1 and a.HAS_ROLE = ? and b.SERVICE_ID = ? \n" +
        "and d.PRODUCT_CODE = ?",
        nativeQuery = true)
    List<ServiceInfo> listServiceInfo(String hasRole, String serviceId, String productCode);

    interface ServiceInfo {
        String getSERVICE_ID();

        String getPRODUCT_CODE();

        String getURL_API();

        String getCONNECTOR_URL();

        String getCONNECTOR_PORT();

        String getMETHOD_ACTION();

        String getUDF1();

        String getUDF2();

        String getUDF3();

        String getUDF4();

        String getUDF5();

        String getUDF6();

        String getUDF7();

        String getUDF8();

    }
}
