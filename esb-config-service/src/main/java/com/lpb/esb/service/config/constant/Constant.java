package com.lpb.esb.service.config.constant;

public class Constant {
    public static final String PKG_ESB_INIT_TRANSACTION = "PKG_ESB_INIT_TRANSACTION";

    public static final String PR_UPDATE_TRANSACTION = "PR_UPDATE_TRANSACTION";

    public static final String PKG_ESB_BILLING = "PKG_ESB_BILLING";

    public static final String INIT_PAYBILL = "init_PayBill";

    public static final String Fn_Request_Transaction
        = "{? = call PKG_ESB_SWITCHING.Fn_Request_Transaction(?,?,?,?,XMLType(?),?,XMLType(?),XMLType(?),?,?,?) }";

    public static final String Fn_Init_Transaction
        = "{? = call PKG_ESB_INIT_TRANSACTION.Fn_Init_Transaction(?,?,?,?,?,XMLType(?),XMLType(?),XMLType(?),XMLType(?),?,?)}";

    public static final String TRANSFER = "TRANSFER";

    public static final String BILLING = "BILLING";

    public static final String PKG_ESB_BILLING_FN_BILLING_LOG = "{call PKG_PARTNER_SERVICE.FN_BILLING_LOG_V2(XMLType(?), ?, ?, ?, ?)}";

    public static final String FUNCTION_LOG_BILLING = "{ ? = call PKG_ESB_BILLING.Fn_Complete_PayBill(?, ?, ?, XMLType(?)) }";

    public static final String FUNCTION_LOG_TRANSFER = "{ ? = call PKG_ESB_TRANSFER.Fn_Complete_Transfer(?, ?, ?, XMLType(?)) }";

    public static final String ESB_REQUEST_TXN= "REQUEST_TXN";

    public static final String ESB_REVERT_TXN= "REVERT_TXN";}

