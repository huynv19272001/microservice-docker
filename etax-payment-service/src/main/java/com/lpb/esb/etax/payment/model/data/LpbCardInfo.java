/**
 * @author Trung.Nguyen
 * @date 07-Jun-2022
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
public class LpbCardInfo {

	@JsonProperty("cardNumber")
	private String cardNumber;

	@JsonProperty("inputType")
	private String inputType;

	@JsonProperty("appId")
	private String appId;

}
