/**
 * @author Trung.Nguyen
 * @date 05-Jun-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LpbCustomer {

	@JsonProperty("BRANCH_CODE")
	private String branchCode;

	@JsonProperty("CUSTOMER_NUMBER")
	private String customerNumber;

	@JsonProperty("CUSTOMER_TYPE")
	private String customerType;

	@JsonProperty("CUSTOMER_NAME")
	private String customerName;

	@JsonProperty("ADDRESS_LINE1")
	private String addressLine1;

	@JsonProperty("ADDRESS_LINE2")
	private String addressLine2;

	@JsonProperty("ADDRESS_LINE3")
	private String addressLine3;

	@JsonProperty("ADDRESS_LINE4")
	private String addressLine4;

	@JsonProperty("COUNTRY")
	private String country;

	@JsonProperty("SHORT_NAME")
	private String shortName;

	@JsonProperty("NATIONALITY")
	private String nationality;

	@JsonProperty("LANGUAGE")
	private String language;

	@JsonProperty("EXPOSURE_COUNTRY")
	private String exposureCountry;

	@JsonProperty("LOCAL_BRANCH")
	private String localBranch;

	@JsonProperty("LIABILITY_NUMBER")
	private String liabilityNumber;

	@JsonProperty("UNIQUE_ID_NAME")
	private String uniqueIdName;

	@JsonProperty("UNIQUE_ID_VALUE")
	private String uniqueIdValue;

	@JsonProperty("ISSUED_DATE")
	private String issuedDate;

	@JsonProperty("ISSUED_PLACE")
	private String issuedPlace;

	@JsonProperty("FROZEN")
	private String frozen;

	@JsonProperty("DECEASED")
	private String deceased;

	@JsonProperty("WHEREABOUTS_UNKNOWN")
	private String whereaboutsUnknown;

	@JsonProperty("CUSTOMER_CATEGORY")
	private String customerCategory;

	@JsonProperty("HO_AC_NO")
	private String hoAcNo;

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

	@JsonProperty("FX_CUST_CLEAN_RISK_LIMIT")
	private String fxCustCleanRiskLimit;

	@JsonProperty("OVERALL_LIMIT")
	private String overallLimit;

	@JsonProperty("FX_CLEAN_RISK_LIMIT")
	private String fxCleanRiskLimit;

	@JsonProperty("CREDIT_RATING")
	private String creditRating;

	@JsonProperty("REVISION_DATE")
	private String revisionDate;

	@JsonProperty("LIMIT_CCY")
	private String limitCcy;

	@JsonProperty("CAS_CUST")
	private String casCust;

	@JsonProperty("LIAB_NODE")
	private String liabNode;

	@JsonProperty("SEC_CUST_CLEAN_RISK_LIMIT")
	private String secCustCleanRiskLimit;

	@JsonProperty("SEC_CLEAN_RISK_LIMIT")
	private String secCleanRiskLimit;

	@JsonProperty("SEC_CUST_PSTL_RISK_LIMIT")
	private String secCustPstlRiskLimit;

	@JsonProperty("SEC_PSTL_RISK_LIMIT")
	private String secPstlRiskLimit;

	@JsonProperty("LIAB_BR")
	private String liabBr;

	@JsonProperty("PAST_DUE_FLAG")
	private String pastDueFlag;

	@JsonProperty("DEFAULT_MEDIA")
	private String defaultMedia;

	@JsonProperty("SSN")
	private String ssn;

	@JsonProperty("SWIFT_CODE")
	private String swiftCode;

	@JsonProperty("LOC_CODE")
	private String locCode;

	@JsonProperty("SHORT_NAME2")
	private String shortName2;

	@JsonProperty("UTILITY_PROVIDER")
	private String utilityProvider;

	@JsonProperty("UTILITY_PROVIDER_ID")
	private String utilityProviderId;

	@JsonProperty("RISK_PROFILE")
	private String riskProfile;

	@JsonProperty("DEBTOR_CATEGORY")
	private String debtorCategory;

	@JsonProperty("FULL_NAME")
	private String fullName;

	@JsonProperty("AML_REQUIRED")
	private String amlRequired;

	@JsonProperty("AML_CUSTOMER_GRP")
	private String amlCustomerGrp;

	@JsonProperty("MAILERS_REQUIRED")
	private String mailersRequired;

	@JsonProperty("GROUP_CODE")
	private String groupCode;

	@JsonProperty("EXPOSURE_CATEGORY")
	private String exposureCategory;

	@JsonProperty("CUST_CLASSIFICATION")
	private String custClassification;

	@JsonProperty("CIF_STATUS")
	private String cifStatus;

	@JsonProperty("CIF_STATUS_SINCE")
	private String cifStatusSince;

	@JsonProperty("INTRODUCER")
	private String introducer;

	@JsonProperty("CUST_CLG_GROUP")
	private String custClgGroup;

	@JsonProperty("CHK_DIGIT_VALID_REQD")
	private String chkDigitValidReqd;

	@JsonProperty("ALG_ID")
	private String algId;

	@JsonProperty("FT_ACCTING_AS_OF")
	private String ftAcctingAsOf;

	@JsonProperty("UNADVISED")
	private String unadvised;

	@JsonProperty("TAX_GROUP")
	private String taxGroup;

	@JsonProperty("CONSOL_TAX_CERT_REQD")
	private String consolTaxCertReqd;

	@JsonProperty("INDIVIDUAL_TAX_CERT_REQD")
	private String individualTaxCertReqd;

	@JsonProperty("CLS_CCY_ALLOWED")
	private String clsCcyAllowed;

	@JsonProperty("CLS_PARTICIPANT")
	private String clsParticipant;

	@JsonProperty("FX_NETTING_CUSTOMER")
	private String fxNettingCustomer;

	@JsonProperty("RISK_CATEGORY")
	private String riskCategory;

	@JsonProperty("FAX_NUMBER")
	private String faxNumber;

	@JsonProperty("EXT_REF_NO")
	private String extRefNo;

	@JsonProperty("CRM_CUSTOMER")
	private String crmCustomer;

	@JsonProperty("CUSTOMER_EMAIL")
	private String customerEmail;

	@JsonProperty("CUSTOMER_MOBILE")
	private String customerMobile;

}
