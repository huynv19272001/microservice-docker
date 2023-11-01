/**
 * @author Trung.Nguyen
 * @date 11-May-2022
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
public class EntryInfo {

	@JsonProperty("accountNumber")
	private String accountNumber;

	@JsonProperty("accountBranch")
	private String accountBranch;

	@JsonProperty("accountCCY")
	private String accountCCY;

	@JsonProperty("accountType")
	private String accountType;

	@JsonProperty("drcrIndicator")
	private String drcrIndicator;

	@JsonProperty("lcyAmount")
	private String lcyAmount;

    /**
     * @author Trung.Nguyen
     * @date 20-Sep-2022
     * description: append these fields for transfer by Citad
     * */
    @JsonProperty("senderInstAccount")
    private String senderInstAccount;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("receiverAddress")
    private String receiverAddress;

    @JsonProperty("receiverAccount")
    private String receiverAccount;

    @JsonProperty("receiverCode")
    private String receiverCode;

}
