/**
 * @author Trung.Nguyen
 * @date 07-Jun-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransFeedback {
	
	@JsonProperty("tthai")
	private String responseCode;

	@JsonProperty("mota")
	private String responseDesc;
	
	@JsonProperty("Magiaodich")
	private String transId;

}
