/**
 * @author Trung.Nguyen
 * @date 10-May-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAccountInfo {
	
	@JsonProperty("acNo")
	private String acNo;
	
	@JsonProperty("branchCode")
	private String branchCode;
	
	@JsonProperty("customerNo")
	private String customerNo;
	
	@JsonProperty("ccy")
	private String ccy;
	
	@JsonProperty("lcyAmount")
	private String lcyAmount;
	
	@JsonProperty("drcrInd")
	private String drcrInd;
	
	@JsonProperty("cardNo")
	private String cardNo;
	
	@JsonProperty("serviceType")
	private String serviceType;

}
