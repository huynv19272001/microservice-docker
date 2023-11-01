package com.lpb.service.vimo.util;

public enum ResponseCodeESB {
    TIMEOUT("090", "Timeout"),
    FAIL_CONNECT("098", "Mất kết nối đối tác"),
    FAIL_ESB("099", "Lỗi không xác định ESB"),
    INVALID_REQUEST("011","Field Input không hợp lệ.");

    private String responseCode;
    private String responseMessage;

    private ResponseCodeESB(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
