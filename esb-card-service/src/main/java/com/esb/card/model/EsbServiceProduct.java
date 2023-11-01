package com.esb.card.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_SERVICE_PRODUCT")
public class EsbServiceProduct implements Serializable {
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "TXN_CODE")
    private String txtCode;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "MAKER_DT")
    private String makerDt;
    @Column(name = "MAKER_ID")
    private String makerId;
    @Column(name = "CHECKER_DT")
    private String checkerDt;
    @Column(name = "CHECKER_ID")
    private String checkerId;
    @Column(name = "TXN_CCY")
    private String txnCCY;
    @Column(name = "UDF1")
    private String udf1;
    @Column(name = "UDF2")
    private String udf2;
    @Column(name = "UDF3")
    private String udf3;
    @Column(name = "UDF4")
    private String udf4;
}
