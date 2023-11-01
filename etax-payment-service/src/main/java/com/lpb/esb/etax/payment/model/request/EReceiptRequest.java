/**
 * @author Trung.Nguyen
 * @date 12-Jul-2022
 * */
package com.lpb.esb.etax.payment.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EReceiptRequest {
	
	@JsonProperty("MaDoiTac")
	private String partnerCode;
	
	@JsonProperty("MaThamChieu")
	private String referenceNumber;
	
	@JsonProperty("ThoiGianGD")
	private String transTime;
	
	@JsonProperty("MaXacThuc")
	private String checksum;

}
