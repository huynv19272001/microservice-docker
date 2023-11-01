/**
 * @author Trung.Nguyen
 * @date 29-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransConfirmation {
	
	@JsonProperty("magiaodich")
	private String appMsgId;
	
	@JsonProperty("otpcode")
	private String otpCode;
	
	@JsonProperty("loaiOTP")
	private String verificationType;

}
