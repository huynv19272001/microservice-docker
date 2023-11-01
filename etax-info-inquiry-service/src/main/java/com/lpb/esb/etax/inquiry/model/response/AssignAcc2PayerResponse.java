/**
 * @author Trung.Nguyen
 * @date 26-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AssignAcc2PayerResponse extends DefaultResponse {

	@JsonProperty("magiaodich")
	private String appMsgId;

	@JsonProperty("loaiOTP")
	private String verificationType;
	
	@JsonProperty("smartOTPUnlockCode")
	private String smartOTPUnlockCode;
	
}
