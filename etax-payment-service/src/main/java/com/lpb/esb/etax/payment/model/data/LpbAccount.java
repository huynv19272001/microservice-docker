/**
 * @author Trung.Nguyen
 * @date 24-May-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbAccount {

	@JsonProperty("BRANCH_CODE")
	private String branchCode;

	@JsonProperty("ACCOUNT_NUMER")
	private String accountNumber;

	@JsonProperty("ACCOUNT_NAME")
	private String accountName;

	@JsonProperty("CUSTOMER_NUMBER")
	private String customerNumber;

	@JsonProperty("ACCOUNT_CCY")
	private String accountCcy;

	@JsonProperty("ACCOUNT_CLASS")
	private String accountClass;

	@JsonProperty("AC_STAT_NO_DR")
	private String acStatNoDr;

	@JsonProperty("AC_STAT_NO_CR")
	private String acStatNoCr;

	@JsonProperty("AC_STAT_BLOCK")
	private String acStatBlock;

	@JsonProperty("AC_STAT_STOP_PAY")
	private String acStatStopPay;

	@JsonProperty("AC_STAT_DORMANT")
	private String acStatDormant;

	@JsonProperty("AC_OPEN_DATE")
	private String acOpenDate;

	@JsonProperty("ALT_AC_NO")
	private String altAcNo;

	@JsonProperty("CHEQUEBOOK_FACILITY")
	private String chequebookFacility;

	@JsonProperty("ATM_FACILITY")
	private String atmFacility;

	@JsonProperty("POS_FACILITY")
	private String posFacility;

	@JsonProperty("PASSBOOK_FACILITY")
	private String passbookFacility;

	@JsonProperty("AC_STAT_FROZEN")
	private String acStatFrozen;

	@JsonProperty("TOD_LIMIT_START_DATE")
	private String todLimitStartDate;

	@JsonProperty("TOD_LIMIT_END_DATE")
	private String todLimitEndDate;

	@JsonProperty("TOD_LIMIT")
	private String todLimit;

	@JsonProperty("DR_GL")
	private String drGl;

	@JsonProperty("CR_GL")
	private String crGl;

	@JsonProperty("RECORD_STAT")
	private String recordStat;

	@JsonProperty("AUTH_STAT")
	private String authStat;

	@JsonProperty("MOD_NO")
	private String modNo;

	@JsonProperty("MAKER_ID")
	private String makerId;

	@JsonProperty("MAKER_STAMP")
	private String makerStamp;

	@JsonProperty("CHECKER_ID")
	private String checkerId;

	@JsonProperty("CHECKER_STAMP")
	private String checkerStamp;

	@JsonProperty("ONCE_AUTH")
	private String onceAuth;

	@JsonProperty("LIMIT_CCY")
	private String limitCcy;

	@JsonProperty("LINE_ID")
	private String lineId;

	@JsonProperty("ACY_CURR_BALANCE")
	private String acyCurrBalance;

	@JsonProperty("ACY_AVL_BAL")
	private String acyAvlBal;

	@JsonProperty("ACY_BLOCK_AMOUNT")
	private String acyBlockAmount;

	@JsonProperty("MIN_BALANCE")
	private String minBalance;

	@JsonProperty("DORMANCY_DATE")
	private String dormancyDate;

	@JsonProperty("DORMANCY_DAYS")
	private String dormancyDays;

	@JsonProperty("ADDRESS1")
	private String address1;

	@JsonProperty("ADDRESS2")
	private String address2;

	@JsonProperty("ADDRESS3")
	private String address3;

	@JsonProperty("ADDRESS4")
	private String address4;

	@JsonProperty("AC_STAT_DE_POST")
	private String acStatDePost;

	@JsonProperty("CLEARING_BANK_CODE")
	private String clearingBankCode;

	@JsonProperty("CLEARING_AC_NO")
	private String clearingAcNo;

	@JsonProperty("ACCOUNT_TYPE")
	private String accountType;

	@JsonProperty("CHEQUEBOOK_NAME1")
	private String chequebookName1;

	@JsonProperty("CHEQUEBOOK_NAME2")
	private String chequebookName2;

	@JsonProperty("ACC_STATUS")
	private String accStatus;

	@JsonProperty("LOCATION")
	private String location;

	@JsonProperty("MEDIA")
	private String media;

	@JsonProperty("SOURCE_SYSTEM")
	private String sourceSystem;

}