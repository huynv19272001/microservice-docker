/**
 * @author Trung.Nguyen
 * @date 12-May-2022
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
public class UploadTransferIO {
	
	@JsonProperty("sourceCode")
	private String sourceCode;
	
	@JsonProperty("branchCode")
	private String branchCode;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("txnCode")
	private String txnCode;
	
	@JsonProperty("valueDt")
	private String valueDt;
	
	@JsonProperty("makerId")
	private String makerId;
	
	@JsonProperty("checkerId")
	private String checkerId;
	
	@JsonProperty("listEntry")
	private List<EntryInfo> listEntry;

}
