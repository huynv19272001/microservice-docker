package com.lpb.esb.etax.payment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InitTaxPaymentResponse extends DefaultResponse {
	
	@JsonProperty("magiaodich")
	private String transId;

	@JsonProperty("loaiOTP")
	private String verificationType;
	
	@JsonProperty("smartOTPUnlockCode")
	private String smartOTPUnlockCode;
	
	@JsonProperty("lephi")
	private String transFee = "0";
	
}
