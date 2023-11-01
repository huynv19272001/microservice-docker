package com.lpb.esb.etax.payment.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESB_ETAX_RECEIPT")
public class EtaxReceiptInfo {

	@Id
	@Column(name = "REFERENCE_NUMBER")
	private String referenceNumber;

	@Column(name = "TRANS_ID")
	private String transId;

	@Column(name = "SERVICE_CODE")
	private String serviceCode;

	@Column(name = "TAX_RECEIPT_CODE")
	private String taxReceiptCode;

	@Column(name = "BENEFIT_ACCOUNT_NUMBER")
	private String benefitAccountNumber;

	@Column(name = "CHANNEL_CODE")
	private String channelCode;

	@Column(name = "TAX_CATEGORY_CODE")
	private String taxCategoryCode;

	@Column(name = "TAX_CATEGORY_NAME")
	private String taxCategoryName;

	@Column(name = "TAX_CODE")
	private String taxCode;

	@Column(name = "TAX_PAYER_NAME")
	private String taxPayerName;

	@Column(name = "TAX_PAYER_IDENTIFICATION")
	private String taxPayerIdentification;

	@Column(name = "TAX_PAYER_ADDRESS")
	private String taxPayerAddress;

	@Column(name = "TAX_PAYER_DISTRICT")
	private String taxPayerDistrict;

	@Column(name = "TAX_PAYER_PROVINCE")
	private String taxPayerProvince;

    @Column(name = "TAX_CODE2")
    private String taxCode2;

    @Column(name = "TAX_PAYER_NAME2")
    private String taxPayerName2;

    @Column(name = "TAX_PAYER_ADDRESS2")
    private String taxPayerAddress2;

    @Column(name = "TAX_PAYER_DISTRICT2")
    private String taxPayerDistrict2;

    @Column(name = "TAX_PAYER_PROVINCE2")
    private String taxPayerProvince2;

	@Column(name = "TAX_INSTITUTION_CODE")
	private String taxInstitutionCode;

	@Column(name = "TAX_INSTITUTION_NAME")
	private String taxInstitutionName;

	@Column(name = "LOCAL_TREASURY_CODE")
	private String localTreasuryCode;

	@Column(name = "TAX_DATE")
	private String taxDate;

	@Column(name = "TAX_NUMBER")
	private String taxNumber;

	@Column(name = "TAX_FEES")
	public String taxFees;

	@Column(name = "TAX_RECEIPT_DETAILS")
	public String taxReceiptDetails;

    @Column(name = "BENEFIT_BANK_NAME")
    public String benefitBankName;

    @Column(name = "PROVINCE")
    public String province;

}
