/**
 * @author Trung.Nguyen
 * @date 29-Jun-2022
 * */
package com.lpb.esb.etax.payment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConfrmTransResponse extends DefaultResponse {
	
	@JsonProperty("ewalletToken")
	private String ewalletToken;

}
