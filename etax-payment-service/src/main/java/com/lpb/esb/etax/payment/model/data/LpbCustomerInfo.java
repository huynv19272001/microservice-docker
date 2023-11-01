/**
 * @author Trung.Nguyen
 * @date 05-Jun-2022
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
public class LpbCustomerInfo {

	@JsonProperty("CustomerNumber")
	private String customerNumber;

	@JsonProperty("UniqueValue")
	private String uniqueValue;

}
