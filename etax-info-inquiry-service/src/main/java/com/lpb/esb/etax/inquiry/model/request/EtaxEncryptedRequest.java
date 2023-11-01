/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtaxEncryptedRequest {
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ESign {

		@JsonProperty("value")
		private String value;

		@JsonProperty("certificates")
		private String certificates;

	}

	@JsonProperty("duLieu")
	private String dataBase64;

	@JsonProperty("signature")
	private ESign eSign;

}
