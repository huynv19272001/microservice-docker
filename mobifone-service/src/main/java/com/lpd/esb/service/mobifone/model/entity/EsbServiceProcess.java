package com.lpd.esb.service.mobifone.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_SERVICE_PROCESS")
public class EsbServiceProcess implements Serializable {
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "EXECUTED_BY")
    private String executedBy;
    @Column(name = "CONNECTOR_NAME")
    private String connectorName;
    @Column(name = "URL_API")
    private String urlApi;
    @Column(name = "CONNECTOR_URL")
    private String connectorUrl;
    @Column(name = "CONNECTOR_PORT")
    private String connectorPort;
    @Column(name = "METHOD_ACTION")
    private String methodAction;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "EFF_DATE")
    private String effDate;
    @Column(name = "UDF1")
    private String udf1;
    @Column(name = "UDF2")
    private String udf2;
    @Column(name = "UDF3")
    private String udf3;
    @Column(name = "UDF4")
    private String udf4;
    @Column(name = "UDF5")
    private String udf5;
    @Column(name = "UDF6")
    private String udf6;
    @Column(name = "UDF7")
    private String udf7;
    @Column(name = "UDF8")
    private String udf8;

    @OneToMany(targetEntity = EsbServiceProduct.class)
    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PRODUCT_CODE")
    private List<EsbServiceProduct> esbServiceProduct;
}
