/**
 * @author Trung.Nguyen
 * @date 12-May-2022
 * */
package com.lpb.esb.etax.inquiry.model.config;

public class GeneralTransConfig {

	// General
	public static final String ETAX_USERID = "ETAX_LPB";
	public static final String VERIFICATION_SMS_OTP = "SMSOTP";
	public static final String VERIFICATION_SMART_OTP = "SMARTOTP";
	public static final String VERIFICATION_E_TOKEN = "TOKEN";

	// Account class - Oracle FlexCubs
	public static final String ACCOUNT_CLASS_V0CNTN = "V0CNTN";
	public static final String ACCOUNT_CLASS_V0ID44 = "V0ID44";
	public static final String ACCOUNT_CLASS_V0IDAK = "V0IDAK";

    // Card type - SmartVista
    public static final String CARD_TYPE_LOCAL_DEBIT = "LOCAL_DEBIT";

    // Card status - SmartVista
    public static final String CARD_STATUS_CSTS0 = "CSTS0";

    // SMS template
    public static final String LINK_OTP_SMS = "Quy khach dang lien ket tai khoan/the mo tai LPB voi ung dung eTAX. KHONG cung cap ma OTP cho bat cu ai de tranh rui ro MAT TIEN. Ma xac thuc OTP la %s";

	// Environment
	public static final String ENV_TEST = "TEST";
	public static final String ENV_PILOT = "PILOT";
	public static final String ENV_PROD = "PROD";

}
