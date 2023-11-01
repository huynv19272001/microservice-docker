/**
 * @author Trung.Nguyen
 * @date 21-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbAccountInfo {

	@JsonProperty("AccountNumber")
	private String accountNumber;

	@JsonProperty("AccountType")
	private String accountType;

}
