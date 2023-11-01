/**
 * @author Trung.Nguyen
 * @date 12-Jul-2022
 * */
package com.lpb.esb.etax.payment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EReceiptResponse {
	
	@JsonProperty("MaLoi")
	private String responseCode;

	@JsonProperty("MoTaLoi")
	private String responseDesc;
	
	@JsonProperty("URLBienLai")
	private String eReceiptUrl;
	
}
