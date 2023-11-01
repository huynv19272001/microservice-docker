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
public class ServiceInfo {
	
	@JsonProperty("serviceId")
	private String serviceId;
	
	@JsonProperty("productCode")
	private String productCode;
	
	@JsonProperty("merchantId")
	private String merchantId;

}
