/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxReceiptDetail {
	
	@JsonProperty("noiDung")
	private String receiptNarration;
	
	@JsonProperty("soTien")
	private String receiptAmount;
	
}
