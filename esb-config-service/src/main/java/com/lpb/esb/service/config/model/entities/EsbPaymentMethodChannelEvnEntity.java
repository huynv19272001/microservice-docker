package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.EsbPaymentMethodChannelEvnId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_PAYMENT_METHOD_CHANNEL_EVN")
@IdClass(EsbPaymentMethodChannelEvnId.class)
public class EsbPaymentMethodChannelEvnEntity implements Serializable {
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Id
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @Id
    @Column(name = "CHANNEL")
    private String channel;
    @Id
    @Column(name = "BILL_PRINT")
    private String billPrint;
    @Basic
    @Column(name = "MAP_EVN")
    private String mapEvn;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
}
