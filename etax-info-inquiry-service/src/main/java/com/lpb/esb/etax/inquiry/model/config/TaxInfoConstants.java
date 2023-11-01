package com.lpb.esb.etax.inquiry.model.config;

public class TaxInfoConstants {

	// Identification Type: CMND, CCCD, HC
	public static final String IDENTIFICATION = "CMND";
	public static final String CITIZEN_IDENTITY = "CCCD";
	public static final String PASSPORT = "HC";
	
	// Core banking identification Type: CHUNG MINH NHAN DAN, CAN CUOC CONG DAN, HO CHIEU
	public static final String CORE_IDENTIFICATION = "CHUNG MINH NHAN DAN";
	public static final String CORE_CITIZEN_IDENTITY = "CAN CUOC CONG DAN";
	public static final String CORE_PASSPORT = "HO CHIEU";
	
	// eTax account authentication or payment method: ACCOUNT or CARD
	public static final String BANK_ACCOUNT = "ACCOUNT";
	public static final String BANK_CARD = "CARD";
	
	// Link or Unlink eTax account: OPEN or CLOSE
	public static final String LINK_ETAX_ACCOUNT = "OPEN";
	public static final String UNLINK_ETAX_ACCOUNT = "CLOSE";
		
}
