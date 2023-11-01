package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by tudv1 on 2022-06-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ReceiptDetail {
    @JsonProperty(value = "noiDung")
    String noiDung;
    @JsonProperty(value = "soTien")
    long soTien;
}
