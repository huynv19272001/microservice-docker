/**
 * @author Trung.Nguyen
 * @date 01-Nov-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SmsGwOut {

    private String messageId;
    private String brandName;
    private String partnerId;
    private String phoneNo;
    private String telco;

}
