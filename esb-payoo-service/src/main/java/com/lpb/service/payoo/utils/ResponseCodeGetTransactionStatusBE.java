package com.lpb.service.payoo.utils;

public enum ResponseCodeGetTransactionStatusBE {
    FAIL("5","Thanh toán thất bại.","005","Giao dịch thất bại"),
    ACCOUNTING_STEP1("4","Đã thanh toán","510","Truy vấn trạng thái giao dịch không thành công"),
    PENDING("3","Đang xử lý","510","Truy vấn trạng thái giao dịch không thành công" ),
    CANCEL("2","Hủy", "005","Giao dịch thất bại"),
    SUCCESSFUL("1", "Thành công","000","Thành công"),
    DOUBT("0","Nghi vấn", "510","Truy vấn trạng thái giao dịch không thành công"),
    INVALID_REQUEST_TIME("2001","Thông tin RequestTime không hợp lệ.","011","Field Input không hợp lệ."),
    INVALID_IP("2005", "IP không hợp lệ.","021","IP không được phép gửi tin"),
    INVALID_SIGNATURE("2004", "Chữ ký điện tử không hợp lệ.","288","Lỗi mã hoá."),
    QUERY_TRANSACTION_FAIL("-1","Xem trạng thái giao dịch bị lỗi.","510","Truy vấn trạng thái giao dịch không thành công"),
    TRANSACTION_NOT_EXIST("-2","Giao dịch không tồn tại","031","Mã giao dịch không tìm thấy"),
    TIME("-3","Lỗi timeout.","090","Timeout");
    private String responseCodePayoo;
    private String responseCode;
    private String responseMessage;

    private String responseMessagePayoo;

    private ResponseCodeGetTransactionStatusBE(String responseCodePayoo, String responseMessagePayoo, String responseCode, String responseMessage) {
        this.responseCodePayoo = responseCodePayoo;
        this.responseMessagePayoo = responseMessagePayoo;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCodePayoo() {
        return responseCodePayoo;
    }

    public void setResponseCodePayoo(String responseCodePayoo) {
        this.responseCodePayoo = responseCodePayoo;
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

    public String getResponseMessagePayoo() {
        return responseMessagePayoo;
    }

    public void setResponseMessagePayoo(String responseMessagePayoo) {
        this.responseMessagePayoo = responseMessagePayoo;
    }
}
