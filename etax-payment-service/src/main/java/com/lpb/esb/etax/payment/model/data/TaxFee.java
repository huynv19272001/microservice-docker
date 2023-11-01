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
public class TaxFee {
	
	@JsonProperty("loaiPhiLePhi")
	private String feeCategory;
	
	@JsonProperty("maPhiLePhi")
	private String feeCategoryCode;
	
	@JsonProperty("tenPhiLePhi")
	private String feeCategoryName;
	
	@JsonProperty("soTien")
	private String feeAmount;

}
