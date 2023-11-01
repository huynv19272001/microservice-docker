package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.ServiceProcessID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by tudv1 on 2021-07-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_SERVICE_PROCESS")
@IdClass(ServiceProcessID.class)
public class EsbServiceProcessEntity implements Serializable {
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Basic
    @Column(name = "ROLE_ID")
    private String roleId;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "EXECUTED_BY")
    private String executedBy;
    @Basic
    @Column(name = "CONNECTOR_NAME")
    private String connectorName;
    @Basic
    @Column(name = "URL_API")
    private String urlApi;
    @Basic
    @Column(name = "CONNECTOR_URL")
    private String connectorUrl;
    @Basic
    @Column(name = "CONNECTOR_PORT")
    private String connectorPort;
    @Basic
    @Column(name = "METHOD_ACTION")
    private String methodAction;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Basic
    @Column(name = "EFF_DATE")
    private Date effDate;
    @Basic
    @Column(name = "UDF1")
    private String udf1;
    @Basic
    @Column(name = "UDF2")
    private String udf2;
    @Basic
    @Column(name = "UDF3")
    private String udf3;
    @Basic
    @Column(name = "UDF4")
    private String udf4;
    @Basic
    @Column(name = "UDF5")
    private String udf5;
    @Basic
    @Column(name = "UDF6")
    private String udf6;
    @Basic
    @Column(name = "UDF7")
    private String udf7;
    @Basic
    @Column(name = "UDF8")
    private String udf8;
//    @OneToMany(targetEntity = EsbServiceProductEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PRODUCT_CODE")
//    private List<EsbServiceProductEntity> esbServiceProduct;
}
