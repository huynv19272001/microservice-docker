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
@Table(name = "ESB_ETAX_PAYMENT")
public class EtaxPaymentInfo {
	
	@Id
	@Column(name = "REFERENCE_NUMBER")
	private String referenceNumber;
	
	@Column(name = "PARTNER_CODE")
	private String partnerCode;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "DELIVERY_FEE")
	private String deliveryFee;
	
	@Column(name = "LANGUAGE")
	private String language;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "NARRATION")
	private String narration;
	
	@Column(name = "TRANS_TIME")
	private String transTime;
	
	@Column(name = "BANK_CODE")
	private String bankCode;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "DEBIT_ACCOUNT_NUMBER")
	private String debitAccountNumber;
	
	@Column(name = "EWALLET_TOKEN")
	private String ewalletToken;
	
	@Column(name = "TRANS_ID")
	private String transId;
	
	@Column(name = "TRANS_PROGRESS")
	private String transProgress;
	
	@Column(name = "TRANS_STATUS")
	private String transStatus;

}
