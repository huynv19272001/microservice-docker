/**
 * @author Trung.Nguyen
 * @date 05-Jun-2022
 * */
package com.lpb.esb.flex.query.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbCustomerInfo {
	
	@JsonProperty("CustomerNumber")
	private String customerNumber;
	
	@JsonProperty("UniqueValue")
	private String uniqueValue;

}
