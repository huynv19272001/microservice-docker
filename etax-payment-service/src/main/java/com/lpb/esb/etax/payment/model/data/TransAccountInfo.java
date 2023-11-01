/**
 * @author Trung.Nguyen
 * @date 11-May-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransAccountInfo {

	@JsonProperty("trnBranch")
	private String trnBranch;

	@JsonProperty("trnDesc")
	private String trnDesc;

	@JsonProperty("accBranch")
	private String accBranch;

	@JsonProperty("lcyAmount")
	private String lcyAmount;

	@JsonProperty("accNo")
	private String accNo;

	@JsonProperty("relatedCustomer")
	private String relatedCustomer;

	@JsonProperty("drcrInd")
	private String drcrInd;

	@JsonProperty("accCcy")
	private String accCcy;

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
