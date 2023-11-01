/**
 * @author Trung.Nguyen
 * @date 27-Apr-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransConfirmation {

	@JsonProperty("Magiaodich")
	private String transId;
	
	@JsonProperty("OTP")
	private String otpCode;
	
	@JsonProperty("loaiOTP")
	private String verificationType;
	
}
