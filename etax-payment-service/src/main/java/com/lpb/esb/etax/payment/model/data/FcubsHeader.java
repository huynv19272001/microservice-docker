/**
 * @author Trung.Nguyen
 * @date 12-May-2022
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
public class FcubsHeader {
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("ubsComp")
	private String ubsComp;
	
	@JsonProperty("branch")
	private String branch;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("msgId")
	private String msgId;
	
	@JsonProperty("moduleId")
	private String moduleId;
	
	@JsonProperty("service")
	private String service;
	
	@JsonProperty("operation")
	private String operation;
	
	@JsonProperty("appId")
	private String appId;
	
}
