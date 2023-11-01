package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbPaymentMethodChannelEvnId implements Serializable {
    private String productCode;
    private String paymentMethod;
    private String channel;
    private String billPrint;
}
