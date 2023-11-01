package com.lpb.esb.service.common.utils.code;

public enum ErrorMessageEng {
    SUCCESS("00", "Success"),
    FAIL("99", "System error"),
    TIMEOUT("90", "Timeout"),
    NODATA("01", "No data"),
    INPUT_ERROR("11", "Invalid input"),
    WRONG_MANY_OTP("43", "OTP is wrong more than the specified number of times"),
    WRONG_OTP("42", "Wrong OTP"),
    EXPIRED_OTP("41", "Expired OTP"),
    AVAILABLE_AMOUNT("44", "Account balance is not enough"),
    DUPLICATE_MESSAGE("45", "Duplicate message"),
    INVALID_ACCOUNT_NUMBER("46", "Invalid account number"),
    OTHER_ERROR("47", "Other error"),
    SERVICE_NOT_EXITS("48", "Service does not exist"),
    WRONG_USER_PASSWORD("49", "Wrong Username, Password"),
    ;

    public final String label;

    public final String description;

    private ErrorMessageEng(String label, String description) {
        this.label = label;
        this.description = description;
    }
}
