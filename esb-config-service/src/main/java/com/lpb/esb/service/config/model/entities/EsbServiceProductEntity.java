package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.ServiceProductID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-07-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_SERVICE_PRODUCT")
@IdClass(ServiceProductID.class)
public class EsbServiceProductEntity implements Serializable {
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "TXN_CODE")
    private String txnCode;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Basic
    @Column(name = "MAKER_ID")
    private String makerId;
    @Basic
    @Column(name = "MAKER_DT")
    private Date makerDt;
    @Basic
    @Column(name = "CHECKER_ID")
    private String checkerId;
    @Basic
    @Column(name = "CHECKER_DT")
    private Date checkerDt;
    @Basic
    @Column(name = "TXN_CCY")
    private String txnCcy;
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
}
