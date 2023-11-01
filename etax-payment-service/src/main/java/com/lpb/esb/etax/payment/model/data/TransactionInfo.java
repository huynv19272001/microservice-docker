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
public class TransactionInfo {
	
	@JsonProperty("transactionId")
	private String transId;
	
	@JsonProperty("trnDesc")
	private String trnDesc;
	
	@JsonProperty("customerNo")
	private String customerNo;
	
	@JsonProperty("userId")
	private String userId;

}
