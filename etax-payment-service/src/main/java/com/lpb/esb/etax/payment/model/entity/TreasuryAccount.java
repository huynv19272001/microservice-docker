package com.lpb.esb.etax.payment.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "V_TREASURY_ACCOUNT")
public class TreasuryAccount {
	
	@Id
	@Column(name = "MA_KB_4SO")
	private String treasury4Code;
	
	@Column(name = "MA_KB_8SO")
	private String treasury8Code;
	
	@Column(name = "TEN_KHOBAC")
	private String treasuryName;
	
	@Column(name = "SOTAIKHOAN")
	private String accountNumber;
	
	@Column(name = "TENTAIKHOAN")
	private String accountName;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;
	
	@Column(name = "BRANCH_NAME")
	private String branchName;
	
	@Column(name = "SO_TK_CHOTT")
	private String glAccountNumber;
	
	@Column(name = "TEN_TK_CHOTT")
	private String glAccountName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "RECORD_STATUS")
	private String recordStatus;
	
	@Column(name = "USER_MODIFY")
	private String userModify;
	
	@Column(name = "DATE_MODIFY")
	private String dateModify;
	
	@Column(name = "SO_TK_PHI_DV")
	private String feeAccountNumber;
	
	@Column(name = "TEN_TK_PHI_DV")
	private String feeAccountName;
	
	@Column(name = "MA_NH_8_SO")
	private String bank8Code;

}
