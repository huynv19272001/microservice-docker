/**
 * @author Trung.Nguyen
 * @date 12-May-2022
 * */
package com.lpb.esb.etax.payment.model.config;

public class UploadTransConfig {

	// General
	public static final String ETAX_USERID = "ETAX_LPB";
	public static final String ETAX_INTER_TRANSFER_SERVICEID = "010010";		// ServiceId must be declared in the ESB_SERVICE table
	public static final String ETAX_CITAD_TRANSFER_SERVICEID = "020010";		// ServiceId must be declared in the ESB_SERVICE table
	public static final String ETAX_PRODUCT_CODE = "ETAX_PAYMENT";				// Product must be declared in ESB_SERVICE_PRODUCT table
	public static final String ETAX_CHANNEL = "ETAX_MOBILE";
    public static final String ETAX_CITAD_TRANSFER_NOSTRO = "000000010001";

	// eTax account authentication or payment method: ACCOUNT or CARD
	public static final String BANK_ACCOUNT = "ACCOUNT";
	public static final String BANK_CARD = "CARD";

	// Initialize transaction
	public static final String CURRENCY_VND = "VND";
	public static final String CR_INDICATOR = "C";
	public static final String DR_INDICATOR = "D";
	public static final String SERVICE_TYPE_DEPOSIT = "DEPOSIT";

	// Upload transfer header
	public static final String FCUBS_HEADER_SOURCE = "FCUBS";
	public static final String FCUBS_HEADER_UBSCOMP = "FCUBS";
	public static final String FCUBS_HEADER_BRANCH = "001";
	public static final String FCUBS_HEADER_MODULEID = "IF";
	public static final String FCUBS_HEADER_SERVICE = "FCUBSInternalService";
	public static final String FCUBS_HEADER_OPERATION = "UploadTransferJRN";
	public static final String FCUBS_HEADER_USERID = "EBANK";
	public static final String FCUBS_HEADER_PASSWORD = "123456";

	// Upload transfer header
	public static final String UPLOAD_TRANSFER_IO_SOURCECODE = "UPEBK";
	public static final String UPLOAD_TRANSFER_IO_TXNCODE = "IBK";
	public static final String UPLOAD_TRANSFER_IO_BRANCHCODE = "001";
	public static final String UPLOAD_TRANSFER_IO_DESCRIPTION = "Thanh toan eTax";
	public static final String UPLOAD_TRANSFER_IO_MAKERID = "LVBSMB";
	public static final String UPLOAD_TRANSFER_IO_CHECKERID = "SYSTEM";

}
