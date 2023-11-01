/**
 * @author Trung.Nguyen
 * @date 10-May-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitTransaction {
	
	@JsonProperty("transactionInfo")
	private TransactionInfo transactionInfo;
	
	@JsonProperty("service")
	private ServiceInfo serviceInfo;
	
	@JsonProperty("partnerInfo")
	private PartnerInfo partnerInfo;
	
	@JsonProperty("postInfo")
	private List<PostAccountInfo> postInfo;

}
