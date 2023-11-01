/**
 * @author Trung.Nguyen
 * @date 26-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpInfo {

	private String userId;
	private String appMsgId;
	private String mobileNo;
	private String otpCode;

}
