/**
 * @author Trung.Nguyen
 * @date 27-Apr-2022
 * */
package com.lpb.esb.etax.payment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {

	@JsonProperty("tthai")
	private String responseCode;

	@JsonProperty("mota")
	private String responseDesc;

}
