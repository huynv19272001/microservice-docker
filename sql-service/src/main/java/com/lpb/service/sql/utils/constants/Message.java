package com.lpb.service.sql.utils.constants;

public enum Message {
    MSG_000("000", "Thành công"),
    MSG_011("011", "Field Input không hợp lệ"),
    MSG_027("027", "PRODUCT || SERVICE ID không hợp lệ");

    private final String errorCode;
    private final String errorDesc;

    Message(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
