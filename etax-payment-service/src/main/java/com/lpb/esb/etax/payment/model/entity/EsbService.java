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
@Table(name = "ESB_SERVICE")
public class EsbService {
	
	@Id
	@Column(name = "SERVICE_ID")
	private String serviceId;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "PROVIDER")
	private String provider;
	
	@Column(name = "MAKERID")
	private String makerId;
	
	@Column(name = "CHECKERID")
	private String checkerId;
	
	@Column(name = "MAKER_DT")
	private String makerDate;		// date
	
	@Column(name = "CHECKER_DT")
	private String checkerDate;		// date
	
	@Column(name = "RECORD_STATS")
	private String recordStats;
	
	@Column(name = "START_DT")
	private String startDate;		// date
	
	@Column(name = "EXPIRED_DT")
	private String expiredDate;		// date
	
	@Column(name = "UDF_1")
	private String udf1;
	
	@Column(name = "UDF_2")
	private String udf2;
	
	@Column(name = "UDF_3")
	private String udf3;
	
	@Column(name = "UDF_4")
	private String udf4;
	
	@Column(name = "SERVICE_TYPE")
	private String serviceType;
	
	@Column(name = "CR_HOLD_AC_NO")
	private String crHoldAcNo;
	
	@Column(name = "CR_HOLD_CCY")
	private String crHoldCcy;
	
	@Column(name = "TXN_BRANCH")
	private String txnBranch;
	
	@Column(name = "CR_HOLD_BRN")
	private String crHoldBrn;
	
	@Column(name = "AUTHEN_OTP")
	private String authenOtp;
	
	@Column(name = "DR_HOLD_AC_NO")
	private String drHoldAcNo;
	
	@Column(name = "DR_HOLD_CCY")
	private String drHoldCcy;
	
	@Column(name = "DR_HOLD_BRN")
	private String drHoldBrn;

}
