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
public class PartnerInfo {
	
	@JsonProperty("txnCode")
	private String txnCode;
	
	@JsonProperty("txnDatetime")
	private String txnDatetime;
	
	@JsonProperty("chanel")
	private String chanel;
	
	@JsonProperty("txnRefNo")
	private String txnRefNo;

}
